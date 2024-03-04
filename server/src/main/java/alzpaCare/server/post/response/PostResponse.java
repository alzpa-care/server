package alzpaCare.server.post.response;

import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.response.MemberSummaryResponse;
import alzpaCare.server.post.entity.Post;
import alzpaCare.server.post.entity.PostType;

import java.time.LocalDate;

public record PostResponse(

        Integer postId,
        PostType postType,
        String title,
        String content,
        String[] imgUrl,
        Integer viewCnt,
        Integer likeCnt,
        String deleteYn,
        Integer commentCnt,
        LocalDate createdAt,
        MemberSummaryResponse member
) {

        public static PostResponse toPostResponse(Post post) {
            return new PostResponse(
                    post.getPostId(),
                    post.getPostType(),
                    post.getTitle(),
                    post.getContent(),
                    post.getImgUrl(),
                    post.getViewCnt(),
                    post.getLikeCnt(),
                    post.getDeleteYn(),
                    post.getCommentCnt(),
                    post.getCreatedAt(),
                    MemberMapper.toMemberSummaryResponse(post.getMember())
            );
        }


}
