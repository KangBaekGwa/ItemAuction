package baekgwa.itemauction.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode {

    //1000 ~
    //User Error
    ADD_USER_ERROR_DUPLICATED_LOGIN_ID(1000, "중복된 아이디 입니다."),
    FIND_USER_ERROR_NOT_FIND(1002, "로그인한 회원의 회원정보가 없습니다."),
    ADD_USER_ERROR_DUPLICATED_NICKNAME(1003, "중복된 닉네임 입니다."),
    ADD_USER_ERROR_DUPLICATED_PHONE(1003, "중복된 전화번호 입니다."),
    ADD_USER_ERROR_DUPLICATED_EMAIL(1003, "중복된 이메일 입니다."),

    //User Profile Error
    FIND_USER_PROFILE_ERROR_NOT_FIND(2000, "로그인한 회원의 회원 프로필 정보가 없습니다."),
    UPDATE_USER_PROFILE_ERROR_DUPLICATED_NICKNAME(2001, "중복된 닉네임 입니다."),
    UPDATE_USER_PROFILE_ERROR_DUPLICATED_PHONE(2022, "중복된 전화번호 입니다."),
    UPDATE_USER_PROFILE_ERROR_DUPLICATED_EMAIL(2003, "중복된 이메일 입니다."),

    NOT_HANDLED_EXCEPTION(9999, "알수없는 오류로 요청이 취소되었습니다."),
    ;

    private final int code;
    private final String message;
}
