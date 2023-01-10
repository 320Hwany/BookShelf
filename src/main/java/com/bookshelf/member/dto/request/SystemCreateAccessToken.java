package com.bookshelf.member.dto.request;

import org.springframework.stereotype.Component;

import static java.util.UUID.*;

@Component
public class SystemCreateAccessToken implements CreateAccessToken {
    @Override
    public String getAccessToken() {
        return randomUUID().toString();
    }
}
