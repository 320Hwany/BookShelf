package com.bookshelf.book.dto.request;

import lombok.Getter;

@Getter
public class CreateLikesAndBookmark {

    private final Long likes = 0L;
    private final boolean bookmark = false;
}
