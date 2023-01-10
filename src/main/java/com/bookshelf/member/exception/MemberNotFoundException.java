package com.bookshelf.member.exception;

import lombok.Getter;

@Getter
public class MemberNotFoundException extends RuntimeException {

    private static final String MESSAGE = "회원을 찾을 수 없습니다";
    private final String statusCode = "404";

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
