package com.bookshelf.contentstore.repository;

import com.bookshelf.contentstore.domain.ContentStore;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentStoreRepository {

    void save(ContentStore contentStore);

    ContentStore getById(Long id);

    List<ContentStore> findContentStoreOfBook(Long bookId);

    void delete(ContentStore contentStore);
}
