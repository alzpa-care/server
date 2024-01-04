package alzpaCare.server.product;

import alzpaCare.server.member.mapper.MemberMapper;

public class ProductMapper {

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
                MemberMapper.toMemberSummaryResponse(product.getSellerMember())
        );
    }


}
