package com.bookshelf.contentstore.repository;

import com.bookshelf.contentstore.domain.ContentStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentStoreJpaRepository extends JpaRepository<ContentStore, Long> {
}
