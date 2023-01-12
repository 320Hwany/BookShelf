package com.bookshelf.book.dto.request;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class SystemLikesAndBookmark implements CreateLikesAndBookmark {

    private Integer likes = 0;

    private Boolean bookmark = false;

    @Override
    public Integer getLikes() {
        return likes;
    }

    @Override
    public Boolean isBookmark() {
        return bookmark;
    }
}
