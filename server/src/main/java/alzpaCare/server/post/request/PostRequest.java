package alzpaCare.server.post.request;

import alzpaCare.server.post.entity.Post;
import alzpaCare.server.post.entity.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequest(

        PostType postType,

        @NotNull(message = "제목은 필수 입력 사항입니다.")
        @NotBlank(message = "제목은 공백일 수 없습니다.")
        @Size(max = 100, message = "제목은 100글자 이내로 작성해주세요.")
        String title,

        @NotNull(message = "내용은 필수 입력 사항입니다.")
        @NotBlank(message = "내용은 공백일 수 없습니다.")
        @Size(max = 10000, message = "내용은 1000글자 이내로 작성해주세요.")
        String content,

        @Size(max = 254, message = "이미지 URL은 254글자 이내로 작성해주세요.")
        String[] imgUrl
) {

        public Post toEntity() {
            return Post.builder()
                    .postType(postType)
                    .title(title())
                    .content(content())
                    .imgUrl(imgUrl())
                    .viewCnt(0)
                    .likeCnt(0)
                    .deleteYn("N")
                    .commentCnt(0)
                    .build();
        }


}
