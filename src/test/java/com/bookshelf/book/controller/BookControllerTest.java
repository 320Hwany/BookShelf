package com.bookshelf.book.controller;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.dto.request.CreateLikesAndBookmark;
import com.bookshelf.book.repository.BookRepository;
import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.repository.MemberRepository;
import com.bookshelf.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;


import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private MemberService memberService;

    @Nested
    @DisplayName("책 등록 테스트 - Controller")
    class SaveBook {

        private Member member = Member.builder()
                .username("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();

        @BeforeEach
        void clean() {
            bookRepository.deleteAll();
            memberRepository.deleteAll();
        }

        @Test
        @DisplayName("인증된 회원만 책을 저장할 수 있습니다 - 실패")
        void saveBook() throws Exception {
            // given
            BookSave bookSave = BookSave.builder()
                    .title("책 제목")
                    .build();
            String json = objectMapper.writeValueAsString(bookSave);

            // expected
            mockMvc.perform(post("/book")
                            .contentType(APPLICATION_JSON)
                            .content(json))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("책 조회 테스트 - Controller")
    class GetBook {

        private Member member = Member.builder()
                .username("회원이름")
                .email("yhwjd99@gmail.com")
                .password("1234")
                .age(25)
                .build();
        @BeforeEach
        void clean() {
            bookRepository.deleteAll();
            memberRepository.deleteAll();
        }

        @Test
        @DisplayName("책 한권을 조회합니다")
        void getBook() throws Exception {
            // given
            memberRepository.save(member);

            Book book = Book.builder()
                    .title("책 제목")
                    .member(member)
                    .createLikesAndBookmark(new TestLikesAndBookmark(1, false))
                    .build();
            bookRepository.save(book);

            // expected
            mockMvc.perform(get("/book/{bookId}", book.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("책 제목"))
                    .andExpect(jsonPath("$.member.username").value("회원이름"))
                    .andExpect(jsonPath("$.likes").value(1))
                    .andExpect(jsonPath("$.bookMark").value(false))
                    .andDo(print());
        }

        @Test
        @DisplayName("책을 등록일자를 기준으로 최신순으로 정렬합니다")
        void getBooksByLatest() throws Exception {
            // given
            memberRepository.save(member);

            List<Book> bookList = IntStream.range(1, 31)
                    .mapToObj(i -> Book.builder()
                            .title("책 제목 " + i)
                            .member(member)
                            .createLikesAndBookmark(new TestLikesAndBookmark(i, false))
                            .build())
                    .collect(Collectors.toList());

            bookRepository.saveAll(bookList);

            // expected
            mockMvc.perform(get("/books-orderBy-latest?page=2"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(10))
                    .andExpect(jsonPath("$[0].title").value("책 제목 20"))
                    .andExpect(jsonPath("$[0].likes").value(20))
                    .andExpect(jsonPath("$[0].bookMark").value(false))
                    .andDo(print());
        }

        @Test
        @DisplayName("책을 좋아요 많은 순으로 정렬합니다")
        void getBooksByLikes() throws Exception {
            // given
            memberRepository.save(member);

            List<Book> bookList = IntStream.range(1, 31)
                    .mapToObj(i -> Book.builder()
                            .title("책 제목 " + i)
                            .member(member)
                            .createLikesAndBookmark(new TestLikesAndBookmark(i, false))
                            .build())
                    .collect(Collectors.toList());

            bookRepository.saveAll(bookList);

            // expected
            mockMvc.perform(get("/books-orderBy-latest?page=2"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(10))
                    .andExpect(jsonPath("$[0].title").value("책 제목 20"))
                    .andExpect(jsonPath("$[0].likes").value(20))
                    .andExpect(jsonPath("$[0].bookMark").value(false))
                    .andDo(print());
        }
    }


    private class TestLikesAndBookmark implements CreateLikesAndBookmark {

        private Integer likes;
        private Boolean bookmark;

        public TestLikesAndBookmark(Integer likes, Boolean bookmark) {
            this.likes = likes;
            this.bookmark = bookmark;
        }

        @Override
        public Integer getLikes() {
            return likes;
        }

        @Override
        public Boolean isBookmark() {
            return bookmark;
        }
    }
}