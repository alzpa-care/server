package alzpaCare.server.post.controller;

import alzpaCare.server.post.entity.Post;
import alzpaCare.server.post.request.PostRequest;
import alzpaCare.server.post.response.PostResponse;
import alzpaCare.server.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> postPost(
            @RequestBody @Valid PostRequest postRequest, Authentication authentication) {

        String username = authentication.getName();

        Post post = postService.createPost(postRequest.toEntity(), username);
        PostResponse postResponse = PostResponse.toPostResponse(post);

        return ResponseEntity.ok(postResponse);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponse> patchPost(
            @PathVariable("postId") Integer postId,
            @RequestBody @Valid PostRequest postRequest,
            Authentication authentication
    ){

        String username = authentication.getName();

        Post post = postService.updatePost(postId, postRequest, username);
        PostResponse postResponse = PostResponse.toPostResponse(post);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(
            @PathVariable("postId") Integer postId) {

        Post post = postService.findPostById(postId);
        PostResponse postResponse = PostResponse.toPostResponse(post);

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping
    public ResponseEntity getPost(
            @RequestParam int postType,
            @RequestParam(defaultValue = "newest") String order) {

        List<Post> posts = postService.getPosts(postType, order);

        List<PostResponse> postResponses = posts.stream()
                .map(PostResponse::toPostResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(postResponses);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable("postId") Integer postId, Authentication authentication) {

        String username = authentication.getName();

        postService.postDelete(postId, username);

        return ResponseEntity.ok("글이 성공적으로 삭제되었습니다.");
    }

}
