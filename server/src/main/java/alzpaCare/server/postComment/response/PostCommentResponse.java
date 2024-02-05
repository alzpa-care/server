package alzpaCare.server.postComment.response;

import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.response.MemberSummaryResponse;
import alzpaCare.server.postComment.entity.PostComment;

import java.time.LocalDate;

public record PostCommentResponse(
        Integer commentId,
        String content,
        LocalDate createdAt,
        MemberSummaryResponse member

) {

    public static PostCommentResponse toCommentResponse(PostComment postComment) {
        return new PostCommentResponse(
                postComment.getCommentId(),
                postComment.getContent(),
                postComment.getCreatedAt(),
                MemberMapper.toMemberSummaryResponse(postComment.getMember())
        );
    }


}
