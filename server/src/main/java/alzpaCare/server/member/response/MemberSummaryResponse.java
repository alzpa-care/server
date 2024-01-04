package alzpaCare.server.member.response;

public record MemberSummaryResponse(
        Integer memberId,
        String imgUrl,
        String nickname
) {
}
