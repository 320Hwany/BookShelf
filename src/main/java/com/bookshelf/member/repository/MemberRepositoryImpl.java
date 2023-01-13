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
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public Member getById(Long id) {
        Member member = memberJpaRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Member getByEmailAndPassword(String email, String password) {
        Member member = memberJpaRepository.findByEmailAndPassword(email, password)
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }

    @Override
    public Boolean checkByMemberLogin(MemberLogin memberLogin) {
        Member member = memberJpaRepository.findByEmailAndPassword(memberLogin.getEmail(), memberLogin.getPassword())
                .orElseThrow(MemberNotFoundException::new);
        return true;
    }

    @Override
    public void deleteAll() {
        memberJpaRepository.deleteAll();
    }

    @Override
    public Long count() {
        return memberJpaRepository.count();
    }

    @Override
    public void delete(Member member) {
        memberJpaRepository.delete(member);
    }
}
