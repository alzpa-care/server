package alzpaCare.server.member.mapper;

import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.response.MemberResponse;
import alzpaCare.server.member.response.MemberSummaryResponse;

public class MemberMapper {

    public static MemberResponse toMemberResponse(Member member) {
        return new MemberResponse(
                member.getEmail(),
                member.getName(),
                member.getNickname(),
                member.getPhoneNumber(),
                member.getBirth(),
                member.getImgUrl(),
                member.getCreatedAt()
        );
    }

    public static MemberSummaryResponse toMemberSummaryResponse(Member member) {
        return new MemberSummaryResponse(
                member.getMemberId(),
                member.getImgUrl(),
                member.getNickname()
        );
    }


}
