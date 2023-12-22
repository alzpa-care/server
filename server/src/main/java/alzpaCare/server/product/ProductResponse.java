package alzpaCare.server.product;

import alzpaCare.server.member.response.MemberSummaryResponse;

import java.time.LocalDate;

public record ProductResponse(

        Integer productId,
        Category category,
        int price,
        String title,
        String content,
        String areas,
        String[] imgUrl,
        String soldOutYn,
        LocalDate createdAt,
        MemberSummaryResponse sellerMember
) {

}
