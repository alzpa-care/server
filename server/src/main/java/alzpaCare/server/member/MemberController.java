package alzpaCare.server.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        // joinRequest 객체를 사용하여 회원가입 로직 수행
        memberService.createNewMember(joinRequest.toEntity());

        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
    }
}
