package alzpaCare.server.member.response;

import java.time.LocalDate;

public record MemberResponse(
        String email,
        String name,
        String nickname,
        String phoneNumber,
        String birth,
        String imgUrl,
        LocalDate createdAt
) {
}
