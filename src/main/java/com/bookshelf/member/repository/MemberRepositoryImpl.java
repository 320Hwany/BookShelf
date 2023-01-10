package com.bookshelf.member.repository;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        return memberJpaRepository.findByName(name);
    }

    @Override
    public Member getByEmailAndPassword(String email, String password) {
        Member member = memberJpaRepository.findByEmailAndPassword(email, password)
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }

    @Override
    public void deleteAll() {
        memberJpaRepository.deleteAll();
    }

    @Override
    public Long count() {
        return memberJpaRepository.count();
    }
}
