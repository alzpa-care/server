package alzpaCare.server.member.service;


import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Authority;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.AuthorityRepository;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.member.request.*;
import alzpaCare.server.patient.event.MemberCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void createNewMember(Member member) {
        // 이메일 중복 체크
        if (isEmailAlreadyExists(member.getEmail())) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EMAIL_ALREADY_EXISTS);
        }

        Authority authority = Authority.builder()
                .role("ROLE_USER")
                .build();
        authorityRepository.save(authority);

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        member.setAuthorities(Collections.singleton(authority));
        memberRepository.save(member);

        applicationEventPublisher.publishEvent(new MemberCreatedEvent(this, member));
    }

    private boolean isEmailAlreadyExists(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.isPresent();
    }

    public String findEmail(FindEmailRequest findEmailRequest) {
        // 이름, 생일, 전화번호를 사용하여 이메일 찾기
        Optional<Member> optionalMember = memberRepository.findByNameAndBirthAndPhoneNumber(
                findEmailRequest.getName(),
                findEmailRequest.getBirth(),
                findEmailRequest.getPhoneNumber()
        );

        if (optionalMember.isPresent()) {
            return optionalMember.get().getEmail();
        } else {
            throw new BusinessLogicException(ExceptionCode.FIND_EMAIL_NOT_FOUND);
        }
    }

    public void findPassword(FindPasswordRequest findPasswordRequest) {
        // 입력된 정보로 회원을 찾음
        Optional<Member> optionalMember = memberRepository.findByEmailAndNameAndPhoneNumberAndBirth(
                findPasswordRequest.getEmail(),
                findPasswordRequest.getName(),
                findPasswordRequest.getPhoneNumber(),
                findPasswordRequest.getBirth()
        );

        if (optionalMember.isPresent()) {
            // 회원이 존재하면 비밀번호를 업데이트
            Member member = optionalMember.get();
            String newPassword = passwordEncoder.encode(findPasswordRequest.getPassword());
            member.setPassword(newPassword);
            memberRepository.save(member);
        } else {
            throw new BusinessLogicException(ExceptionCode.FIND_PASSWORD_MEMBER_NOT_FOUND);
        }
    }

    @Transactional(readOnly = true)
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member updateMember(UpdateRequest updateRequest, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Optional.ofNullable(updateRequest.nickname())
                .ifPresent(member::setNickname);

        Optional.ofNullable(updateRequest.phoneNumber())
                .ifPresent(member::setPhoneNumber);

        return memberRepository.save(member);
    }

    public Member updateImgUrl(ImgUrlRequest imgUrlRequest, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Optional.ofNullable(imgUrlRequest.imgUrl())
                .ifPresent(member::setImgUrl);


        return memberRepository.save(member);
    }

    public void updatePassword(PasswordRequest passwordRequest, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        String newPassword = passwordEncoder.encode(passwordRequest.getPassword());
        member.setPassword(newPassword);

        memberRepository.save(member);
    }


    public void updateDelete(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        member.setDeleteYn("Y");
        memberRepository.save(member);
    }
}
