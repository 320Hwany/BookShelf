package com.bookshelf.member.dto.response;

import com.bookshelf.member.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class MemberResponse {

    @NotBlank(message = "회원 이름을 입력해주세요")
    private String name;

    @Email(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Min(value = 0, message = "0이상의 수를 입력해주세요")
    private Integer age;

    public MemberResponse(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.age = member.getAge();
    }
}
