package com.bookshelf.member.domain;

import com.bookshelf.member.dto.request.MemberSignup;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String email;
    private String password;
    private Integer age;

    public Member(MemberSignup memberSignup) {
        this.name = memberSignup.getName();
        this.email = memberSignup.getEmail();
        this.password = memberSignup.getPassword();
        this.age = memberSignup.getAge();
    }

    @Builder
    public Member(String name, String email, String password, Integer age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}

