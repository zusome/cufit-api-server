package com.official.cufitapi.common.config

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val code: HttpStatus,
    val message: String
) {
    // 400
    ALREADY_MATCHED_CANDIDATE(HttpStatus.BAD_REQUEST, "이미 매칭된 후보입니다."),
    NON_EXISTENT_MATCH(HttpStatus.BAD_REQUEST, "존재하지 않는 주선입니다."),
    INVALID_INVITATION_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 초대 코드입니다."),
    NOT_MATCHED_SMS_AUTH_CODE(HttpStatus.BAD_REQUEST, "SMS 인증번호가 일치하지 않습니다."),
    DAILY_MAXIMUM_MATCHING_COUNT(HttpStatus.BAD_REQUEST, "하루에 3회 이상 매칭을 할 수 없습니다."),
    CONNECTION_REQUEST_SAME_GENDER(HttpStatus.BAD_REQUEST, "동성과는 매칭을 할 수 없습니다."),
    YEAR_OF_BIRTH_INVALID(HttpStatus.BAD_REQUEST, "생년이 유효하지 않습니다."),
    INVALID_SMS_AUTH_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 SMS 인증번호입니다."),
    INVALID_SUFFIX(HttpStatus.BAD_REQUEST, "유효하지 않은 접미사입니다."),
    INVALID_RELATION_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 관계 유형입니다."),


    // 401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_AUTHORIZATION(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    // 404
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    NOT_FOUND_MATCH(HttpStatus.NOT_FOUND, "존재하지 않는 주선입니다."),
    NOT_FOUND_RECEIVER(HttpStatus.NOT_FOUND, "존재하지 않는 Receiver."),
    NOT_FOUND_SENDER(HttpStatus.NOT_FOUND, "존재하지 않는 Sender."),
    NOT_FOUND_DEVICE_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 Device Token입니다."),
    NOT_FOUND_CANDIDATE(HttpStatus.NOT_FOUND, "존재하지 않는 매칭 후보입니다."),


    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    RUNTIME_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.")

}
