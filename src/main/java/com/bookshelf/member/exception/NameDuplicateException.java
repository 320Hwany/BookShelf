package com.bookshelf.member.exception;

import lombok.Getter;

@Getter
public class NameDuplicateException extends RuntimeException {

    private static final String MESSAGE = "이미 가입된 회원입니다";
    private final String statusCode = "400";

    public NameDuplicateException() {
        super(MESSAGE);
    }
}
