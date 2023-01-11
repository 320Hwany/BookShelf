package com.bookshelf.book.exception;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {

    private static final String MESSAGE = "책정보를 찾을 수 없습니다";

    private final String statusCode = "404";
    public BookNotFoundException() {
        super(MESSAGE);
    }
}
