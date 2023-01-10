package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionJpaRepository sessionJpaRepository;

    @Override
    public void save(Session session) {
        sessionJpaRepository.save(session);
    }

    @Override
    public void deleteAll() {
        sessionJpaRepository.deleteAll();
    }

    @Override
    public Long count() {
        return sessionJpaRepository.count();
    }
}
