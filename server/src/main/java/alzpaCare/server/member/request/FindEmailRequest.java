package alzpaCare.server.member.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FindEmailRequest(

        @NotNull(message = "이름은 필수 입력 사항입니다.")
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        @Size(max = 20, message = "이름은 20글자 이내로 입력해주세요.")
        String name,

        @NotNull(message = "생일은 필수 입력 사항입니다.")
        @NotBlank(message = "생일은 공백일 수 없습니다.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "'YYYY-MM-DD' 형식으로 입력해주세요.")
        String birth,

        @NotNull(message = "전화번호는 필수 입력 사항입니다.")
        @NotBlank(message = "전화번호는 공백일 수 없습니다.")
        @Pattern(regexp = "^[0-9]+$", message = "숫자만 입력해야 합니다.")
        String phoneNumber
) {

        public String getName() {
                return name;
        }

        public String getBirth() {
                return birth;
        }

        public String getPhoneNumber() {
                return phoneNumber;
        }


}
