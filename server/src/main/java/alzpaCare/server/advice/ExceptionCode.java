package alzpaCare.server.advice;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_EMAIL_ALREADY_EXISTS(409, "이미 가입된 이메일입니다."),
    FIND_EMAIL_NOT_FOUND(404, "이메일을 찾을 수 없습니다. 가입하지 않은 아이디이거나 입력 정보가 정확하지 않을 수 있습니다."),
    FIND_PASSWORD_MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다. 가입하지 않은 아이디이거나 입력 정보가 정확하지 않을 수 있습니다."),
    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
    INVALID_CREDENTIALS(404,"아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요."),
    PATIENT_NOT_FOUND(404, "환자를 찾을 수 없습니다."),
    INVALID_CATEGORY(404, "잘못된 카테고리 입니다."),
    PRODUCT_NOT_FOUND(404, "productId를 확인해 주세요. 중고거래글을 찾을 수 없습니다."),
    PRODUCT_NOT_DELETE_SOLD_OUT(404,"거래가 완료된 이후에는 글 삭제가 불가능합니다."),
    PRODUCT_NOT_DELETE_MEMBER(404,"작성자 외에는 글을 삭제할 수 없습니다."),
    COMMENT_NOT_FOUND(404, "댓글을 찾을 수 없습니다."),
    SELLER_MEMBER_NOT_MATCH(404, "판매자만 구매자를 결정할 수 있습니다."),
    UNAUTHORIZED_ACCESS(404, "댓글 작성자만 댓글을 수정/삭제할 수 있습니다."),
    POST_MEMBER_NOT_MATCH(404, "게시글 작성자만 글을 수정할 수 있습니다."),
    POST_NOT_FOUND(404,"게시글을 찾을 수 없습니다."),
    ALREADY_EXIST_VOTE(409, "이미 좋아요를 누르셨습니다."),
    POST_LIKE_NOT_FOUNT(409, "좋아요를 누르지 않은 글에 좋아요를 취소할 수 없습니다.");



    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }


}
