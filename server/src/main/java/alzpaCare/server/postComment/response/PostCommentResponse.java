package alzpaCare.server.postComment.response;

import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.response.MemberSummaryResponse;
import alzpaCare.server.postComment.entity.PostComment;

import java.time.LocalDate;

public record PostCommentResponse(
        Integer commentId,
        String content,
        LocalDate createdAt,
        String commentType,
        MemberSummaryResponse member

) {

    public static PostCommentResponse toCommentResponse(PostComment postComment) {
        String type = postComment.getParent() == null ? "댓글" : "대댓글";
        return new PostCommentResponse(
                postComment.getCommentId(),
                postComment.getContent(),
                postComment.getCreatedAt(),
                type,
                MemberMapper.toMemberSummaryResponse(postComment.getMember())
        );
    }


}
