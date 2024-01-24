package alzpaCare.server.comment.response;

import alzpaCare.server.comment.entity.Comment;
import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.response.MemberSummaryResponse;

import java.time.LocalDate;

public record CommentResponse(
        Integer commentId,
        String content,
        LocalDate createdAt,
        MemberSummaryResponse member

) {

    public static CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getContent(),
                comment.getCreatedAt(),
                MemberMapper.toMemberSummaryResponse(comment.getMember())
        );
    }


}
