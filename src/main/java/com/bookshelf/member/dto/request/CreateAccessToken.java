package com.bookshelf.member.dto.request;

import org.springframework.stereotype.Component;

@Component
public interface CreateAccessToken {

    String getAccessToken();
}
