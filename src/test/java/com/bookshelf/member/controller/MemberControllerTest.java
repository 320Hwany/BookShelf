package com.bookshelf.member.controller;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.repository.MemberRepository;
import com.bookshelf.member.repository.SessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    @DisplayName("회원가입 테스트 - Controller")
    class Signup {

        @BeforeEach
        void clean() {
            sessionRepository.deleteAll();
            memberRepository.deleteAll();
        }
        @Test
        @DisplayName("조건에 맞거나 중복되지 않은 회원이면 회원가입이 가능합니다 - 성공")
        void signup() throws Exception {
            // given
            MemberSignup memberSignup = MemberSignup.builder()
                    .name("회원이름")
                    .email("yhwjd99@gmail.com")
                    .password("1234")
                    .age(25)
                    .build();

            String json = objectMapper.writeValueAsString(memberSignup);

            // expected
            mockMvc.perform(post("/signup")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @Test
        @DisplayName("조건에 맞지 않으면 회원가입에 실패합니다 - 실패")
        void signupFailByValid() throws Exception {
            // given
            MemberSignup memberSignup = MemberSignup.builder()
                    .name("")
                    .email("yhwjd99")
                    .password("1234")
                    .age(25)
                    .build();

            String json = objectMapper.writeValueAsString(memberSignup);

            // expected
            mockMvc.perform(post("/signup")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value("400"))
                    .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                    .andExpect(jsonPath("$.validation.name").value("회원 이름을 입력해주세요"))
                    .andExpect(jsonPath("$.validation.email").value("이메일을 입력해주세요"))
                    .andDo(print());
        }

        @Test
        @DisplayName("이미 존재하는 회원이면 가입하실 수 없습니다 - 실패")
        void signupFailByDuplication() throws Exception {
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
            String json = objectMapper.writeValueAsString(memberSignup);

            // expected
            mockMvc.perform(post("/signup")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value("400"))
                    .andExpect(jsonPath("$.message").value("이미 가입된 회원입니다"))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("회원 로그인 테스트 - Controller")
    class Login {

        @BeforeEach
        void clean() {
            sessionRepository.deleteAll();
            memberRepository.deleteAll();
        }

        @Test
        @DisplayName("회원정보가 일치하면 로그인에 성공합니다 - 성공")
        void login() throws Exception {
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
            String json = objectMapper.writeValueAsString(memberLogin);

            // expected
            mockMvc.perform(post("/login")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("회원이름"))
                    .andExpect(jsonPath("$.email").value("yhwjd99@gmail.com"))
                    .andExpect(jsonPath("$.password").value("1234"))
                    .andExpect(jsonPath("$.age").value(25))
                    .andDo(print());
        }

        @Test
        @DisplayName("조건에 맞지 않으면 로그인이 되지 않습니다 - 실패")
        void loginFailByValid() throws Exception {
            // given
            Member member = Member.builder()
                    .name("회원이름")
                    .email("yhwjd99")
                    .password("")
                    .age(25)
                    .build();

            MemberLogin memberLogin = MemberLogin.builder()
                    .email("yhwjd99")
                    .password("")
                    .build();

            memberRepository.save(member);
            String json = objectMapper.writeValueAsString(memberLogin);

            // expected
            mockMvc.perform(post("/login")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value("400"))
                    .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                    .andExpect(jsonPath("$.validation.email").value("이메일을 입력해주세요"))
                    .andExpect(jsonPath("$.validation.password").value("비밀번호를 입력해주세요"))
                    .andDo(print());
        }

        @Test
        @DisplayName("조건에 맞지 않으면 로그인이 되지 않습니다 - 실패")
        void loginFailByDuplication() throws Exception {
            // given
            MemberLogin memberLogin = MemberLogin.builder()
                    .email("yhwjd99@gmail.com")
                    .password("1234")
                    .build();

            String json = objectMapper.writeValueAsString(memberLogin);

            // expected
            mockMvc.perform(post("/login")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value("404"))
                    .andExpect(jsonPath("$.message").value("회원을 찾을 수 없습니다"))
                    .andDo(print());
        }
    }
}