package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Session;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository {

    void save(Session session);
}
