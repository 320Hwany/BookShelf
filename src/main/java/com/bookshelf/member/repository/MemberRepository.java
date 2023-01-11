package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository {

    void save(Member member);

    Optional<Member> findByName(String name);

    Member getById(Long id);

    Optional<Member> findById(Long id);

    Member getByEmailAndPassword(String email, String password);

    void deleteAll();

    Long count();

    void delete(Member member);
}
