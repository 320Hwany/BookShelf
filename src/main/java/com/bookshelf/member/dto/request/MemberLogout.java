package com.bookshelf.member.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberLogout {

    private Long id;

    @Builder
    public MemberLogout(Long id) {
        this.id = id;
    }
}
