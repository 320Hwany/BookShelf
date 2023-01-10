package com.bookshelf.member.service;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.exception.MemberNotFoundException;
import com.bookshelf.member.repository.MemberRepository;
import com.bookshelf.member.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }
    @Test
    @DisplayName("회원가입 테스트 - 성공")
    void signup() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .name("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();

        // when
        memberService.signup(memberSignup);

        // then
        assertThat(memberRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("이미 존재하는 회원인지 검증합니다")
    void validateDuplication() {
        // given
        Member member = Member.builder()
                .name("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();

        MemberSignup memberSignup = MemberSignup.builder()
                .name("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();

        memberRepository.save(member);

        // when
        boolean isPresent = memberService.validateDuplication(memberSignup);

        // then
        assertThat(isPresent).isTrue();
    }

    @Test
    @DisplayName("회원정보가 일치하면 로그인에 성공합니다")
    void login() {
        // given
        Member member = Member.builder()
                .name("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();

        MemberLogin memberLogin = MemberLogin.builder()
                .email("yhwjd99@gmail.com")
                .password("1234")
                .build();

        memberRepository.save(member);

        // when
        memberService.login(memberLogin);

        // then
        assertThat(sessionRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("회원정보가 일치하지 않으면 로그인에 실패합니다")
    void loginFail() {
        // given
        MemberLogin memberLogin = MemberLogin.builder()
                .email("yhwjd99@gmail.com")
                .password("1234")
                .build();

        // expected
        assertThrows(MemberNotFoundException.class,
                () -> memberService.login(memberLogin));
    }
}