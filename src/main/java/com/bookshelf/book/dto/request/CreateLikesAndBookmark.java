package com.bookshelf.book.dto.request;

import org.springframework.stereotype.Component;

@Component
public interface CreateLikesAndBookmark {

    Integer getLikes();

    Boolean isBookmark();
}
