package com.bookshelf.member.controller;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.domain.Session;
import com.bookshelf.member.dto.request.CreateAccessToken;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.dto.request.MemberSignup;
import com.bookshelf.member.dto.request.MemberUpdate;
import com.bookshelf.member.service.MemberService;
import com.bookshelf.member.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final SessionService sessionService;
    private final CreateAccessToken createAccessToken;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid MemberSignup memberSignup) {
        memberService.signup(memberSignup);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid MemberLogin memberLogin) {
        Member member = memberService.getByMemberLogin(memberLogin);
        Session session = sessionService.makeSession(member, createAccessToken);
        ResponseCookie cookie = session.setCookie();
        return ResponseEntity.ok()
                .header(SET_COOKIE, cookie.toString())
                .build();
    }

    @PatchMapping("/update/{memberId}")
    public void updateMember(@PathVariable Long memberId, @RequestBody @Valid MemberUpdate memberUpdate) {
        memberService.update(memberId, memberUpdate);
    }

    @DeleteMapping("/delete/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.delete(memberId);
    }
}
