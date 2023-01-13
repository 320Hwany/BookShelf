package com.bookshelf.member.controller;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.*;
import com.bookshelf.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid MemberSignup memberSignup) {
        memberService.signup(memberSignup);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid MemberLogin memberLogin,
                      HttpServletRequest httpServletRequest) {
        memberService.checkByMemberLogin(memberLogin);
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("memberLogin", memberLogin);
    }

    @PatchMapping("/member")
    public void updateMember(MemberSession memberSession, @RequestBody @Valid MemberUpdate memberUpdate,
                             HttpSession httpSession) {
        memberService.update(memberSession.getId(), memberUpdate);
        httpSession.invalidate();
    }

    @DeleteMapping("/member")
    public void deleteMember(MemberSession memberSession) {
        memberService.delete(memberSession.getId());
    }

    @PostMapping("/logout")
    public void logout(MemberSession memberSession, HttpServletRequest request, HttpSession httpSession) {
        Member member = memberService.getById(memberSession.getId());
        HttpSession session = request.getSession(false);
        session.invalidate();
    }
}
