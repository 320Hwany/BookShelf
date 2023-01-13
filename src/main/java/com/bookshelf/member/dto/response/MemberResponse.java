package com.bookshelf.member.dto.response;

import com.bookshelf.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private String username;

    private String email;

    private String password;

    private Integer age;

    public MemberResponse(Member member) {
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.age = member.getAge();
    }
}
