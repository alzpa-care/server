package alzpaCare.server.member.mapper;

import alzpaCare.server.member.Member;
import alzpaCare.server.member.response.MemberResponse;

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


}
