package alzpaCare.server.member;

import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.request.FindEmailRequest;
import alzpaCare.server.member.request.FindPasswordRequest;
import alzpaCare.server.member.request.JoinRequest;
import alzpaCare.server.member.request.UpdateRequest;
import alzpaCare.server.member.response.MemberResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Member member = memberService.findByEmail(username);

        MemberResponse memberResponse = MemberMapper.toMemberResponse(member);

        return ResponseEntity.ok(memberResponse);
    }

    @PatchMapping("/member")
    public ResponseEntity<MemberResponse> patchMember(
            @RequestBody @Valid UpdateRequest updateRequest, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Member member = memberService.updateMember(updateRequest, username);

        MemberResponse updatedMemberResponse = MemberMapper.toMemberResponse(member);

        return ResponseEntity.ok(updatedMemberResponse);
    }


}
