package com.bookshelf.member.domain;

import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.dto.request.MemberUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
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

    public void update(MemberUpdate memberUpdate) {
        this.name = memberUpdate.getName();
        this.email = memberUpdate.getEmail();
        this.password = memberUpdate.getPassword();
        this.age = memberUpdate.getAge();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(name, member.name)
                && Objects.equals(email, member.email) && Objects.equals(password, member.password)
                && Objects.equals(age, member.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, age);
    }
}

