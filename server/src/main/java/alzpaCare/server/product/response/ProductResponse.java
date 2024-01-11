package alzpaCare.server.product.response;

import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.response.MemberSummaryResponse;
import alzpaCare.server.product.entity.Category;
import alzpaCare.server.product.entity.Product;

import java.time.LocalDate;

public record ProductResponse(

        Integer productId,
        Category category,
        Integer price,
        String title,
        String content,
        String areas,
        String[] imgUrl,
        String soldOutYn,
        LocalDate createdAt,
        Integer commentCount,
        MemberSummaryResponse sellerMember
) {
        public static ProductResponse toProductResponse(Product product) {
            return new ProductResponse(
                    product.getProductId(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getTitle(),
                    product.getContent(),
                    product.getAreas(),
                    product.getImgUrl(),
                    product.getSoldOutYn(),
                    product.getCreatedAt(),
                    product.getCommentCount(),
                    MemberMapper.toMemberSummaryResponse(product.getSellerMember())
            );
        }


}
