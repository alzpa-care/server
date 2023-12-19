package alzpaCare.server.security.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(

        @NotNull(message = "이메일은 필수 입력 사항입니다.")
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        @Email(message = "올바른 이메일 주소 형식으로 작성해주세요.")
        @Size(max = 50, message = "이메일은 50글자 이내로 입력해주세요.")
        String email,

        @NotNull(message = "비밀번호는 필수 입력 사항입니다.")
        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,20}$",
                message = "영문, 숫자를 포함한 8자 이상 20자 이하의 비밀번호를 입력해주세요.")
        String password
) {

        public String getEmail() {
                return email;
        }

        public String getPassword() {
                return password;
        }


}
