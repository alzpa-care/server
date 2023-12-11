package alzpaCare.server.member;


import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void createNewMember(Member member) {
        // 이메일 중복 체크
        if (isEmailAlreadyExists(member.getEmail())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EMAIL_ALREADY_EXISTS);
        }
//        String hashedPassword = passwordEncoder.encode(newMember.getPassword());
//        newMember.setPassword(hashedPassword);
        memberRepository.save(member);
    }

    private boolean isEmailAlreadyExists(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.isPresent();
    }

}
