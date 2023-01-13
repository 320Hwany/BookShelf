package com.bookshelf.contentstore.exception;

import lombok.Getter;

@Getter
public class ContentStoreNotFoundException extends RuntimeException{

    private static final String MESSAGE = "책 저장소를 찾을 수 없습니다";

    private final String statusCode = "404";
    public ContentStoreNotFoundException() {
        super(MESSAGE);
    }
}
