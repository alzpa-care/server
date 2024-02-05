package alzpaCare.server.postComment.request;

import alzpaCare.server.postComment.entity.PostComment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCommentRequest(

        @NotNull(message = "내용은 필수 입력 사항입니다.")
        @NotBlank(message = "댓글은 공백일 수 없습니다.")
        @Size(max = 1000, message = "댓글은 1000글자 이내로 작성해주세요.")
        String content

) {

        public PostComment toEntity() {
            return PostComment.builder()
                .content(content())
                .deleteYn("N")
                .build();
        }


}
