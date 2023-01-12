package com.bookshelf.contentstore.repository;

import com.bookshelf.contentstore.domain.ContentStore;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentStoreRepository {

    void save(ContentStore contentStore);
}
