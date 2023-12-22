package alzpaCare.server.member.controller;

import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.request.*;
import alzpaCare.server.member.service.MemberService;
import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.response.MemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody @Valid JoinRequest joinRequest) {
        memberService.createNewMember(joinRequest.toEntity());

        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/find/id")
    public ResponseEntity<String> findId(@RequestBody @Valid FindEmailRequest findEmailRequest) {
        String foundEmail = memberService.findEmail(findEmailRequest);

        return ResponseEntity.ok(foundEmail);
    }

    @PostMapping("/find/pw")
    public ResponseEntity<String> findPassword(@RequestBody @Valid FindPasswordRequest findPasswordRequest) {
        memberService.findPassword(findPasswordRequest);

        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    @GetMapping("/member")
    public ResponseEntity<MemberResponse> getMember(Authentication authentication) {
        String username = authentication.getName();

        Member member = memberService.findMemberByEmail(username);

        MemberResponse memberResponse = MemberMapper.toMemberResponse(member);

        return ResponseEntity.ok(memberResponse);
    }

    @PatchMapping("/member")
    public ResponseEntity<MemberResponse> patchMember(
            @RequestBody @Valid UpdateRequest updateRequest, Authentication authentication) {

        String username = authentication.getName();

        Member member = memberService.updateMember(updateRequest, username);

        MemberResponse updatedMemberResponse = MemberMapper.toMemberResponse(member);

        return ResponseEntity.ok(updatedMemberResponse);
    }

    @PatchMapping("/member/url")
    public ResponseEntity<MemberResponse> patchImgUrl(
            @RequestBody @Valid ImgUrlRequest imgUrlRequest, Authentication authentication) {

        String username = authentication.getName();

        Member member = memberService.updateImgUrl(imgUrlRequest, username);

        MemberResponse updatedMemberResponse = MemberMapper.toMemberResponse(member);

        return ResponseEntity.ok(updatedMemberResponse);
    }

    @PatchMapping("/member/pw")
    public ResponseEntity<String> patchPassword(
            @RequestBody @Valid PasswordRequest passwordRequest, Authentication authentication) {

        String username = authentication.getName();

        memberService.updatePassword(passwordRequest, username);

        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    @PatchMapping("/withdrawals")
    public ResponseEntity<String> patchDelete(Authentication authentication) {

        String username = authentication.getName();

        memberService.updateDelete(username);

        return ResponseEntity.ok("회원탈퇴가 성공적으로 완료되었습니다.");
    }


}
