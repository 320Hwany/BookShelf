package com.bookshelf.book.domain;

import com.bookshelf.book.dto.request.BookSave;
import com.bookshelf.book.dto.request.CreateLikesAndBookmark;
import com.bookshelf.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Long likes;

    private boolean bookMark;

    public Book(Member member, BookSave bookSave, CreateLikesAndBookmark createLikeAndBookmark) {
        this.title = bookSave.getTitle();
        this.member = member;
        this.likes = createLikeAndBookmark.getLikes();
        this.bookMark = createLikeAndBookmark.isBookmark();
    }

    @Builder
    public Book(String title, Member member, CreateLikesAndBookmark createLikesAndBookmark) {
        this.title = title;
        this.member = member;
        this.likes = createLikesAndBookmark.getLikes();
        this.bookMark = createLikesAndBookmark.isBookmark();
    }
}
