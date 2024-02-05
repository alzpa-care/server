package alzpaCare.server.postComment.controller;

import alzpaCare.server.postComment.entity.PostComment;
import alzpaCare.server.postComment.request.PostCommentRequest;
import alzpaCare.server.postComment.response.PostCommentResponse;
import alzpaCare.server.postComment.service.PostCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static alzpaCare.server.postComment.response.PostCommentResponse.toCommentResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postComment")
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping("/{postId}")
    public ResponseEntity<PostCommentResponse> postComment(
            @PathVariable("postId") Integer postId,
            @Valid @RequestBody PostCommentRequest postCommentRequest, Authentication authentication) {

        String username = authentication.getName();

        PostComment postComment = postCommentService.createComment(postId, postCommentRequest.toEntity(), username);
        PostCommentResponse postCommentResponse = toCommentResponse(postComment);

        return ResponseEntity.ok(postCommentResponse);
    }

    @PostMapping("/reply/{commentId}")
    public ResponseEntity<PostCommentResponse> postReply(
            @PathVariable("commentId") Integer commentId,
            @Valid @RequestBody PostCommentRequest postCommentRequest, Authentication authentication) {

        String username = authentication.getName();
        PostComment reply = postCommentService.createReply(commentId, postCommentRequest.toEntity(), username);
        PostCommentResponse postCommentResponse = toCommentResponse(reply);

        return ResponseEntity.ok(postCommentResponse);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<PostCommentResponse>> getCommentsByPost(
            @PathVariable("postId") Integer postId) {

        List<PostCommentResponse> postCommentRespons = postCommentService.getCommentResponsesByPost(postId);

        return ResponseEntity.ok(postCommentRespons);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<PostCommentResponse> patchComment(
            @PathVariable("commentId") Integer commentId,
            @Valid @RequestBody PostCommentRequest postCommentRequest, Authentication authentication) {

        String username = authentication.getName();

        PostComment updatedPostComment = postCommentService.updateComment(commentId, postCommentRequest.toEntity(), username);
        PostCommentResponse postCommentResponse = PostCommentResponse.toCommentResponse(updatedPostComment);

        return ResponseEntity.ok(postCommentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Integer commentId, Authentication authentication) {

        String username = authentication.getName();

        postCommentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }


}
