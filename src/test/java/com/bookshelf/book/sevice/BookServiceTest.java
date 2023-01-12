package com.bookshelf.book.sevice;

import com.bookshelf.book.domain.Book;
import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.dto.request.CreateLikesAndBookmark;
import com.bookshelf.book.dto.response.BookResponse;
import com.bookshelf.book.repository.BookRepository;
import com.bookshelf.member.domain.Member;
import com.bookshelf.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void clean() {
        bookRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("아이디로부터 책 찾기 테스트 - 성공")
    @Transactional
    void getById() {
        // given
        Member member = new Member();
        memberRepository.save(member);

        Book book = Book.builder()
                .title("책 제목")
                .member(member)
                .createLikesAndBookmark(new TestLikesAndBookmark(1, false))
                .build();

        bookRepository.save(book);

        // when
        Book getBook = bookService.getById(book.getId());

        // then
        assertThat(getBook).isEqualTo(book);
    }

    @Test
    @DisplayName("책 저장 테스트 - 성공")
    @Transactional
    void save() {
        // given
        Member member = new Member();
        memberRepository.save(member);

        BookSave bookSave = BookSave.builder()
                .title("책 제목")
                .build();

        // when
        bookService.save(member.getId(), bookSave);

        // then
        assertThat(bookRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("책 최신순으로 찾기 - 성공")
    @Transactional
    void findBooksByLatest() {
        // given
        Member member = new Member();
        memberRepository.save(member);
        List<Book> bookList = IntStream.range(1, 31)
                .mapToObj(i -> Book.builder()
                        .title("책 제목 " + i)
                        .member(member)
                        .createLikesAndBookmark(new TestLikesAndBookmark(i, false))
                        .build())
                .collect(Collectors.toList());

        bookRepository.saveAll(bookList);

        // when
        List<BookResponse> bookResponsesByLatest = bookService.findBookResponsesByLikes(2);

        // then
        assertThat(bookResponsesByLatest.size()).isEqualTo(10);
        assertThat(bookResponsesByLatest.get(0).getTitle()).isEqualTo("책 제목 20");
    }

    @Test
    @DisplayName("책 좋아요 많은순으로 찾기 - 성공")
    @Transactional
    void findBooksByLikes() {
        // given
        Member member = new Member();
        memberRepository.save(member);
        List<Book> bookList = IntStream.range(1, 31)
                .mapToObj(i -> Book.builder()
                        .title("책 제목 " + i)
                        .member(member)
                        .createLikesAndBookmark(new TestLikesAndBookmark(i, false))
                        .build())
                .collect(Collectors.toList());

        bookRepository.saveAll(bookList);

        // when
        List<BookResponse> bookResponsesByLatest = bookService.findBookResponsesByLikes(2);

        // then
        assertThat(bookResponsesByLatest.size()).isEqualTo(10);
        assertThat(bookResponsesByLatest.get(0).getTitle()).isEqualTo("책 제목 20");
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