package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Session> findByAccessToken(String accessToken) {
        return sessionJpaRepository.findByAccessToken(accessToken);
    }

    @Override
    public List<Session> findAllByMemberId(Long memberId) {
        List<Session> sessionList = sessionJpaRepository.findAllByMemberId(memberId);
        return sessionList;
    }

    @Override
    public void deleteList(List<Session> sessionList) {
        for (Session session : sessionList) {
            sessionJpaRepository.delete(session);
        }
    }
}
