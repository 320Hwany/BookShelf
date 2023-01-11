package com.bookshelf.book.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class BookContentStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_content_store_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Long number;

    private String content;
}
