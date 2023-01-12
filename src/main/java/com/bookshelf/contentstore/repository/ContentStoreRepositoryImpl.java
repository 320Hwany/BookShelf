package com.bookshelf.contentstore.repository;

import com.bookshelf.contentstore.domain.ContentStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ContentStoreRepositoryImpl implements ContentStoreRepository {

    private final ContentStoreJpaRepository contentStoreJpaRepository;

    @Override
    public void save(ContentStore contentStore) {
        contentStoreJpaRepository.save(contentStore);
    }
}
