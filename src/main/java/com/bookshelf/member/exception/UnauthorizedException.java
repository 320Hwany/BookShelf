package com.bookshelf.member.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {

    private static final String MESSAGE = "로그인 후 이용해주세요";
    private final String statusCode = "401";

    public UnauthorizedException() {
        super(MESSAGE);
    }
}
