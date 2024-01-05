package alzpaCare.server.productComment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productComment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{productId}")
    public ResponseEntity<Comment> postComment(
            @PathVariable("productId") Integer productId,
            @Valid @RequestBody CommentRequest commentRequest, Authentication authentication) {

        String username = authentication.getName();
        Comment comment = commentService.createComment(productId, commentRequest.toEntity(), username);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }



}
