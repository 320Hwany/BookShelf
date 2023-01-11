package com.bookshelf.book.dto.request;

import lombok.Builder;
import lombok.Getter;

import static java.lang.Math.max;

@Getter
public class BookSearch {

    private Integer page;
    private Integer limit;

    @Builder
    public BookSearch(Integer page, BooksLimit booksLimit) {
        this.page = page;
        this.limit = booksLimit.getLimit();
    }

    public Integer getOffset() {
        return (max(page - 1, 0) * limit);
    }
}
