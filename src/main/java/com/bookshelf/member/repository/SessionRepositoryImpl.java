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
}
