package com.bookshelf.contentstore.dto.request;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class ContentSave {

    @Min(value = 0, message = "0이상의 수를 입력해주세요")
    private Integer page;

    @NotBlank(message = "내용을 입력해주세요")
    private String content;
}
