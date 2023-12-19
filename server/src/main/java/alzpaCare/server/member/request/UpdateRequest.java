package alzpaCare.server.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateRequest(

        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        @Size(max = 12, message = "닉네임은 12글자 이내로 입력해주세요.")
        String nickname,

        @NotBlank(message = "전화번호는 공백일 수 없습니다.")
        @Pattern(regexp = "^[0-9]+$", message = "숫자만 입력해야 합니다.")
        String phoneNumber

) {
}
