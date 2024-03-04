package alzpaCare.server.productComment.response;

import alzpaCare.server.productComment.entity.Comment;
import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.response.MemberSummaryResponse;

import java.time.LocalDate;

public record CommentResponse(
        Integer commentId,
        String content,
        LocalDate createdAt,
        String commentType,
        MemberSummaryResponse member

) {

    public static CommentResponse toCommentResponse(Comment comment) {
        String type = comment.getParent() == null ? "댓글" : "대댓글";

        return new CommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedAt(),
                type,
                MemberMapper.toMemberSummaryResponse(comment.getMember())
        );
    }


}
