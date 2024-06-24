package com.travel.toy3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // 잘못된 형식
    INCORRECT_USERNAME_FORMAT(HttpStatus.BAD_REQUEST.value(), "잘못된 ID 형식입니다. ID는 이메일 형식이어야 합니다."),

    // 유효성 검증 실패
    INVALID_ADDRESS(HttpStatus.NOT_FOUND.value(), "입력하신 주소의 정보가 존재하지 않습니다."),
    INVALID_TRIP(HttpStatus.NOT_FOUND.value(), "입력하신 여행 id에 해당하는 여행 정보가 존재하지 않습니다."),
    INVALID_ITINERARY(HttpStatus.NOT_FOUND.value(), "입력하신 여정 id에 해당하는 여정 정보가 존재하지 않습니다."),
    INVALID_DESTINATION_SEARCH_RESULT(HttpStatus.NOT_FOUND.value(), "입력하신 여행지에 해당하는 여행 정보가 존재하지 않습니다."),
    INVALID_USERNAME_SEARCH_RESULT(HttpStatus.NOT_FOUND.value(), "입력하신 사용자가 작성한 여행 정보가 존재하지 않습니다."),
    INVALID_COMMENT_SEARCH_RESULT(HttpStatus.NOT_FOUND.value(), "입력하신 댓글 id에 해당하는 댓글이 존재하지 않습니다."),

    // 회원가입 실패
    DUPLICATED_USERNAME(HttpStatus.CONFLICT.value(), "이미 존재하는 ID입니다. 다른 ID를 입력하세요."),

    // 인증 실패
    INVALID_USERNAME(HttpStatus.UNAUTHORIZED.value(), "존재하지 않는 ID입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED.value(), "잘못된 비밀번호입니다."),

    // 인증 관련 잘못된 요청
    UNACCEPTABLE_LOGIN_REQUEST(HttpStatus.BAD_REQUEST.value(),"로그인 상태에서는 로그인을 할 수 없습니다."),
    UNACCEPTABLE_JOIN_REQUEST(HttpStatus.BAD_REQUEST.value(),"로그인 상태에서는 회원가입을 할 수 없습니다."),
    UNACCEPTABLE_LOGOUT_REQUEST(HttpStatus.BAD_REQUEST.value(),"로그아웃 상태에서는 로그아웃을 할 수 없습니다."),

    // 권한 없음
    NO_ACCESS_PERMISSION(HttpStatus.UNAUTHORIZED.value(), "접근 권한이 없습니다. 로그인이 필요합니다."),
    NO_ADD_ITINERARY_PERMISSION(HttpStatus.FORBIDDEN.value(), "여정 등록 권한이 없습니다. 본인이 작성한 여행 정보에만 여정 등록이 가능합니다."),
    NO_EDIT_PERMISSION(HttpStatus.FORBIDDEN.value(), "수정 권한이 없습니다. 본인이 작성한 정보만 수정이 가능합니다."),

    // 우리가 정의한 예외가 아닌 경우
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버에 오류가 발생했습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),
    INCORRECT_JSON_FORMAT(HttpStatus.BAD_REQUEST.value(), "잘못된 요청 (JSON) 형식입니다."),

    // 단순히 안내가 필요한 경우
    EMPTY_OWN_LIST(HttpStatus.OK.value(), "아직 작성하신 여행 정보가 없습니다."),
    EMPTY_LIKE_LIST(HttpStatus.OK.value(), "아직 좋아요를 누르신 여행 정보가 없습니다.");

    private final Integer code;
    private final String message;
}
