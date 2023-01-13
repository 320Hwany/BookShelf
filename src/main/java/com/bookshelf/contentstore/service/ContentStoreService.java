package com.bookshelf.contentstore.service;

import com.bookshelf.book.domain.Book;
import com.bookshelf.contentstore.domain.ContentStore;
import com.bookshelf.contentstore.dto.request.ContentUpdate;
import com.bookshelf.contentstore.dto.response.ContentStoreResponse;
import com.bookshelf.contentstore.dto.request.ContentSave;
import com.bookshelf.contentstore.repository.ContentStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ContentStoreService {

    private final ContentStoreRepository contentStoreRepository;

    @Transactional
    public void save(Book book, ContentSave contentSave) {
        ContentStore contentStore = ContentStore.builder()
                .book(book)
                .contentSave(contentSave)
                .build();
        contentStoreRepository.save(contentStore);
    }

    public List<ContentStoreResponse> findContentStoreOfBook(Long bookId) {
        return contentStoreRepository.findContentStoreOfBook(bookId).stream()
                .map(ContentStoreResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(Long bookContentId, ContentUpdate contentUpdate) {
        ContentStore contentStore = contentStoreRepository.getById(bookContentId);
        contentStore.update(contentUpdate);
    }

    @Transactional
    public void delete(Long bookContentId) {
        ContentStore contentStore = contentStoreRepository.getById(bookContentId);
        contentStoreRepository.delete(contentStore);
    }
}
