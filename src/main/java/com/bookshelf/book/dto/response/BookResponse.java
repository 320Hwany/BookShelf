package com.bookshelf.book.dto.response;

import com.bookshelf.book.domain.Book;
import com.bookshelf.member.domain.Member;
import lombok.Getter;

@Getter
public class BookResponse {

    private String title;

    private Member member;

    private Integer likes;

    private boolean bookMark;

    public BookResponse(Book book) {
        this.title = book.getTitle();
        this.member = book.getMember();
        this.likes = book.getLikes();
        this.bookMark = book.isBookMark();
    }
}
