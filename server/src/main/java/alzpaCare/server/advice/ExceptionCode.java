package alzpaCare.server.advice;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_EMAIL_ALREADY_EXISTS(409, "이미 가입된 이메일입니다."),
    FIND_EMAIL_NOT_FOUND(404, "이메일을 찾을 수 없습니다. 가입하지 않은 아이디이거나 입력 정보가 정확하지 않을 수 있습니다."),
    FIND_PASSWORD_MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다. 가입하지 않은 아이디이거나 입력 정보가 정확하지 않을 수 있습니다."),
    MEMBER_NOT_FOUND(404, "Member not found"),
    INVALID_CREDENTIALS(404,"아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요."),

    //이위에는 사용중인 코드
    MEMBER_EXISTS(409, "Member exists"),
    WISHLIST_NOT_FOUND(404, "Wishlist not found"),
    TRADE_NOT_FOUND(404, "Trade not found"),
    FIXED_NOT_FOUND(404, "fixed not found"),
    TOTAL_NOT_FOUND(404, "Total not found"),
    TRADE_MEMBER_NOT_MATCH(404,"Trade memberId not match"),
    FIXED_MEMBER_NOT_MATCH(404,"Fixed memberId not match"),
    WISHLIST_MEMBER_NOT_MATCH(404, "Wishlist memberId not match"),
    TOTAL_MEMBER_NOT_MATCH(404, "Total memberId not match");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }


}
