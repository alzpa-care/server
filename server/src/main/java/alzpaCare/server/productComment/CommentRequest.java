package alzpaCare.server.productComment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentRequest(

        @NotNull(message = "내용은 필수 입력 사항입니다.")
        @NotBlank(message = "댓글은 공백일 수 없습니다.")
        @Size(max = 1000, message = "댓글은 1000글자 이내로 작성해주세요.")
        String content,

        @NotNull(message = "댓글 대댓글 여부는 필수 입력 사항입니다.")
        int depth,

        @NotNull(message = "그룹은 필수 입력 사항입니다.")
        int groupId,

        @NotNull(message = "순서는 필수 입력 사항입니다.")
        int groupOrder
) {

        public Comment toEntity() {
            return Comment.builder()
                        .content(content())
                        .depth(depth())
                        .groupId(groupId())
                        .groupOrder(groupOrder())
                        .build();
        }


}
