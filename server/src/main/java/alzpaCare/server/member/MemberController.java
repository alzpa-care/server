package alzpaCare.server.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
