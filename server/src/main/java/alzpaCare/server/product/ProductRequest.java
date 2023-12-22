package alzpaCare.server.product;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(

        @NotNull(message = "카테고리는 필수 입력 사항입니다.")
        @NotBlank(message = "카테고리는 공백일 수 없습니다.")
        String category,

        @NotNull(message = "가격은 필수 입력 사항입니다.")
        @Min(value = 1, message = "가격은 1 이상이어야 합니다.")
        Integer price,

        @NotNull(message = "제목은 필수 입력 사항입니다.")
        @NotBlank(message = "제목은 공백일 수 없습니다.")
        @Size(max = 100, message = "제목은 100글자 이내로 작성해주세요.")
        String title,

        @NotNull(message = "내용은 필수 입력 사항입니다.")
        @NotBlank(message = "내용은 공백일 수 없습니다.")
        @Size(max = 10000, message = "내용은 1000글자 이내로 작성해주세요.")
        String content,

        @NotNull(message = "지역은 필수 입력 사항입니다.")
        @NotBlank(message = "지역은 공백일 수 없습니다.")
        @Size(max = 100, message = "지역은 100글자 이내로 작성해주세요.")
        String areas,

        @Size(max = 254, message = "이미지 URL은 254글자 이내로 작성해주세요.")
        String[] imgUrl
) {

        public Product toEntity() {
            try {
                return Product.builder()
                        .category(Category.valueOf(category()))
                        .price(price())
                        .title(title())
                        .content(content())
                        .areas(areas())
                        .imgUrl(imgUrl())
                        .soldOutYn("N")
                        .build();
            } catch (IllegalArgumentException e) {
                throw new BusinessLogicException(ExceptionCode.INVALID_CATEGORY);
            }
        }


}
