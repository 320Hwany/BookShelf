package com.bookshelf.member.service;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.dto.request.MemberUpdate;
import com.bookshelf.member.dto.response.MemberResponse;
import com.bookshelf.member.exception.MemberNotFoundException;
import com.bookshelf.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }
    @Test
    @DisplayName("회원가입 테스트 - 성공")
    void signup() {
        // given
        MemberSignup memberSignup = MemberSignup.builder()
                .username("회원이름")
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
                .username("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();

        MemberSignup memberSignup = MemberSignup.builder()
                .username("회원이름")
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
    @DisplayName("입력한 로그인 정보가 맞으면 회원정보를 가져옵니다 - 성공")
    void getByMemberLogin() {
        // given
        Member member = Member.builder()
                .username("회원이름")
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
        Member getMember = memberService.getByMemberLogin(memberLogin);

        // then
        assertThat(getMember).isEqualTo(member);
    }

    @Test
    @DisplayName("입력한 정보로 회원을 수정합니다 - 성공")
    void update() {
        // given
        Member member = Member.builder()
                .username("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();

        MemberUpdate memberUpdate = MemberUpdate.builder()
                .username("회원 수정 이름")
                .email("yhwjd99@naver.com")
                .password("4321")
                .age(20)
                .build();

        memberRepository.save(member);

        // when
        MemberResponse memberResponse = memberService.update(member.getId(), memberUpdate);

        // then
        assertThat(memberResponse.getUsername()).isEqualTo("회원 수정 이름");
        assertThat(memberResponse.getEmail()).isEqualTo("yhwjd99@naver.com");
        assertThat(memberResponse.getPassword()).isEqualTo("4321");
        assertThat(memberResponse.getAge()).isEqualTo(20);
    }

    @Test
    @DisplayName("입력한 아이디에 대한 회원정보가 없으면 수정이 불가합니다 - 실패")
    void updateFail() {
        // given
        MemberUpdate memberUpdate = MemberUpdate.builder()
                .username("회원 수정 이름")
                .email("yhwjd99@naver.com")
                .password("4321")
                .age(20)
                .build();

        // expected
        assertThrows(MemberNotFoundException.class,
                () -> memberService.update(1L, memberUpdate));
    }

//    @Test
//    @DisplayName("입력한 아이디가 존재하면 삭제에 성공합니다 - 성공")
//    void delete() {
//        // given
//        Member member = Member.builder()
//                .name("회원이름")
//                .email("yhwjd99@gmail.com")
//                .password("1234")
//                .age(25)
//                .build();
//
//        memberRepository.save(member);
//        sessionRepository.save(Session.builder()
//                .member(member)
//                .createAccessToken(testCreateAccessToken)
//                .build());
//
//        // when
//        memberService.delete(member.getId());
//        Optional<Member> optionalMember = memberRepository.findById(member.getId());
//
//        // then
//        assertThat(optionalMember).isEmpty();
//    }

//    @Test
//    @DisplayName("입력한 아이디가 존재하지 않으면 삭제가 불가합니다 - 실패")
//    void deleteFail() {
//        // expected
//        Assertions.assertThrows(MemberNotFoundException.class,
//                () -> memberService.delete(1L));
//    }
//
//    private class TestCreateAccessToken implements CreateAccessToken {
//
//        private final String MESSAGE = "testToken";
//        @Override
//        public String getAccessToken() {
//            return MESSAGE;
//        }
//    }
}