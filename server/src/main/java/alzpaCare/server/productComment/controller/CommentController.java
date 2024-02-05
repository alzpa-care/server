package alzpaCare.server.productComment.controller;

import alzpaCare.server.productComment.request.CommentRequest;
import alzpaCare.server.productComment.response.CommentResponse;
import alzpaCare.server.productComment.service.CommentService;
import alzpaCare.server.productComment.entity.Comment;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static alzpaCare.server.productComment.response.CommentResponse.toCommentResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productComment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{productId}")
    public ResponseEntity<CommentResponse> postComment(
            @PathVariable("productId") Integer productId,
            @Valid @RequestBody CommentRequest commentRequest, Authentication authentication) {

        String username = authentication.getName();

        Comment comment = commentService.createComment(productId, commentRequest.toEntity(), username);
        CommentResponse commentResponse = toCommentResponse(comment);

        return ResponseEntity.ok(commentResponse);
    }

    @PostMapping("/reply/{commentId}")
    public ResponseEntity<CommentResponse> postReply(
            @PathVariable("commentId") Integer commentId,
            @Valid @RequestBody CommentRequest commentRequest, Authentication authentication) {

        String username = authentication.getName();
        Comment reply = commentService.createReply(commentId, commentRequest.toEntity(), username);
        CommentResponse commentResponse = toCommentResponse(reply);

        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByProduct(
            @PathVariable("productId") Integer productId, Authentication authentication) {

        String username = authentication.getName();
        List<CommentResponse> commentResponses = commentService.getCommentResponsesByProduct(productId, username);

        return ResponseEntity.ok(commentResponses);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponse> patchComment(
            @PathVariable("commentId") Integer commentId,
            @Valid @RequestBody CommentRequest commentRequest, Authentication authentication) {

        String username = authentication.getName();

        Comment updatedComment = commentService.updateComment(commentId, commentRequest.toEntity(), username);
        CommentResponse commentResponse = CommentResponse.toCommentResponse(updatedComment);

        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Integer commentId, Authentication authentication) {

        String username = authentication.getName();

        commentService.deleteComment(commentId, username);
        return ResponseEntity.noContent().build();
    }


}
