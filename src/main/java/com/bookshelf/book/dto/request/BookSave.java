package com.bookshelf.book.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class BookSave {

    @NotBlank(message = "책 제목을 입력해주세요")
    private String title;

    @Builder
    public BookSave(String title) {
        this.title = title;
    }
}
