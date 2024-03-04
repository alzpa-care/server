package alzpaCare.server.postLike;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postLike")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<String> likePost(
            @PathVariable("postId") Integer postId, Authentication authentication
    ) {

        String username = authentication.getName();
        postLikeService.addLikePost(username, postId);

        return ResponseEntity.ok("좋아요가 등록되었습니다.");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostLike(
            @PathVariable("postId") Integer postId, Authentication authentication
    ) {

        String username = authentication.getName();
        postLikeService.deleteLikePost(username, postId);

        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }


}
