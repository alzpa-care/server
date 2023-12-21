package alzpaCare.server.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PasswordRequest(

        @NotNull(message = "비밀번호는 필수 입력 사항입니다.")
        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,20}$",
                message = "영문, 숫자를 포함한 8자 이상 20자 이하의 비밀번호를 입력해주세요.")
        String password
) {
        public String getPassword() {
                return password;
        }


}
