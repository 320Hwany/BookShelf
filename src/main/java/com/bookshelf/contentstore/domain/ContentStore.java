package com.bookshelf.contentstore.domain;

import com.bookshelf.book.domain.Book;
import com.bookshelf.contentstore.dto.request.ContentSave;
import com.bookshelf.contentstore.dto.request.ContentUpdate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Entity
public class ContentStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_content_store_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer page;

    private String content;

    @Builder
    public ContentStore(Book book, ContentSave contentSave) {
        this.book = book;
        this.page = contentSave.getPage();
        this.content = contentSave.getContent();
    }

    public void update(ContentUpdate contentUpdate) {
        this.page = contentUpdate.getPage();
        this.content = contentUpdate.getContent();
    }
}
