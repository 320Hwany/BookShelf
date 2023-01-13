package com.bookshelf.contentstore.repository;

import com.bookshelf.book.domain.QBook;
import com.bookshelf.contentstore.domain.ContentStore;
import com.bookshelf.contentstore.domain.QContentStore;
import com.bookshelf.contentstore.exception.ContentStoreNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bookshelf.book.domain.QBook.book;
import static com.bookshelf.contentstore.domain.QContentStore.contentStore;

@RequiredArgsConstructor
@Repository
public class ContentStoreRepositoryImpl implements ContentStoreRepository {

    private final ContentStoreJpaRepository contentStoreJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void save(ContentStore contentStore) {
        contentStoreJpaRepository.save(contentStore);
    }

    @Override
    public ContentStore getById(Long id) {
        return contentStoreJpaRepository.findById(id)
                .orElseThrow(ContentStoreNotFoundException::new);
    }

    @Override
    public List<ContentStore> findContentStoreOfBook(Long bookId) {
        return jpaQueryFactory.selectFrom(contentStore)
                .leftJoin(contentStore.book, book)
                .fetchJoin()
                .where(contentStore.book.id.eq(book.id))
                .orderBy(contentStore.page.asc())
                .fetch();
    }

    @Override
    public void delete(ContentStore contentStore) {
        contentStoreJpaRepository.delete(contentStore);
    }
}
