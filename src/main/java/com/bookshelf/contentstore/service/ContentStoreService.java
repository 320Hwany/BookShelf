package com.bookshelf.contentstore.service;

import com.bookshelf.book.domain.Book;
import com.bookshelf.contentstore.domain.ContentStore;
import com.bookshelf.contentstore.dto.request.ContentSave;
import com.bookshelf.contentstore.repository.ContentStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ContentStoreService {

    private final ContentStoreRepository contentStoreRepository;

    public void save(Book book, ContentSave contentSave) {
        ContentStore contentStore = ContentStore.builder()
                .book(book)
                .contentSave(contentSave)
                .build();
        contentStoreRepository.save(contentStore);
    }
}
