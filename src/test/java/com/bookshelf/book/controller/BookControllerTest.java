package com.bookshelf.book.controller;

import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.repository.BookRepository;
import com.bookshelf.member.domain.Member;
import com.bookshelf.member.domain.Session;
import com.bookshelf.member.dto.request.CreateAccessToken;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Nested
    @DisplayName("책 등록 테스트 - Controller")
    class SaveBook {

        private Member member = Member.builder()
                .name("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();

        @BeforeEach
        void clean() {
            bookRepository.deleteAll();
            sessionRepository.deleteAll();
            memberRepository.deleteAll();
        }

        @Test
        @DisplayName("조건에 맞으면 책 정보가 저장됩니다 - 성공")
        void saveBook() throws Exception {
            // given
            TestCreateAccessToken testCreateAccessToken = new TestCreateAccessToken();
            Session session = Session.builder()
                    .member(member)
                    .createAccessToken(testCreateAccessToken)
                    .build();
            memberRepository.save(member);
            sessionRepository.save(session);
            ResponseCookie cookie = session.setCookie();

            BookSave bookSave = BookSave.builder()
                    .title("책 제목")
                    .build();
            String json = objectMapper.writeValueAsString(bookSave);

            // expected
            mockMvc.perform(MockMvcRequestBuilders.post("/book")
                            .cookie(new Cookie("SESSION", cookie.getValue()))
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isOk())
                    .andDo(print());
        }
    }


    private class TestCreateAccessToken implements CreateAccessToken {

        private final String MESSAGE = "testToken";
        @Override
        public String getAccessToken() {
            return MESSAGE;
        }
    }
}