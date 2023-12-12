package alzpaCare.server.member;

import jakarta.validation.constraints.*;

public record JoinRequest(

        @NotNull(message = "이메일은 필수 입력 사항입니다.")
        @NotBlank(message = "이메일은 공백일 수 없습니다.")
        @Email(message = "올바른 이메일 주소 형식으로 작성해주세요.")
        @Size(max = 50, message = "이메일은 50글자 이내로 입력해주세요.")
        String email,

        @NotNull(message = "비밀번호는 필수 입력 사항입니다.")
        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,20}$",
                message = "영문, 숫자를 포함한 8자 이상 20자 이하의 비밀번호를 입력해주세요.")
        String password,

        @NotNull(message = "이름은 필수 입력 사항입니다.")
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        @Size(max = 20, message = "이름은 20글자 이내로 입력해주세요.")
        String name,

        @NotNull(message = "닉네임은 필수 입력 사항입니다.")
        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        @Size(max = 12, message = "닉네임은 12글자 이내로 입력해주세요.")
        String nickname,

        @NotNull(message = "전화번호는 필수 입력 사항입니다.")
        @NotBlank(message = "전화번호는 공백일 수 없습니다.")
        @Pattern(regexp = "^[0-9]+$", message = "숫자만 입력해야 합니다.")
        String phoneNumber,

        @NotNull(message = "생일은 필수 입력 사항입니다.")
        @NotBlank(message = "생일은 공백일 수 없습니다.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "'YYYY-MM-DD' 형식으로 입력해주세요.")
        String birth,

        @NotNull(message = "이용약관은 필수 입력 사항입니다.")
        @NotBlank(message = "이용약관은 공백일 수 없습니다.")
        @Pattern(regexp = "^[YN]$", message = "'Y' 또는 'N'이어야 합니다.")
        String termsOfServiceYn,

        @NotNull(message = "개인정보처리방침은 필수 입력 사항입니다.")
        @NotBlank(message = "개인정보처리방침은 공백일 수 없습니다.")
        @Pattern(regexp = "^[YN]$", message = "'Y' 또는 'N'이어야 합니다.")
        String privacyPolicyYn,

        @NotNull(message = "위치기반서비스는 필수 입력 사항입니다.")
        @NotBlank(message = "위치기반서비스는 공백일 수 없습니다.")
        @Pattern(regexp = "^[YN]$", message = "'Y' 또는 'N'이어야 합니다.")
        String locationYn,

        @NotNull(message = "이메일동의는 필수 입력 사항입니다.")
        @NotBlank(message = "이메일동의는 공백일 수 없습니다.")
        @Pattern(regexp = "^[YN]$", message = "'Y' 또는 'N'이어야 합니다.")
        String emailYn
){

        public Member toEntity() {
            return Member.builder()
                    .email(email())
                    .password(password())
                    .name(name())
                    .nickname(nickname())
                    .phoneNumber(phoneNumber())
                    .birth(birth())
                    .termsOfServiceYn(termsOfServiceYn())
                    .privacyPolicyYn(privacyPolicyYn())
                    .locationYn(locationYn())
                    .emailYn(emailYn())
                    .build();
        }

}