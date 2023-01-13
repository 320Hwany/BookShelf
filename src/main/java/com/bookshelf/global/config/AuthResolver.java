package com.bookshelf.global.config;

import com.bookshelf.member.domain.Member;
import com.bookshelf.member.dto.request.MemberLogin;
import com.bookshelf.member.dto.request.MemberSession;
import com.bookshelf.member.exception.UnauthorizedException;
import com.bookshelf.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            throw new UnauthorizedException();
        }
        MemberLogin memberLogin = (MemberLogin) session.getAttribute("memberLogin");
        Member member = memberService.getByMemberLogin(memberLogin);

        return MemberSession
                .builder()
                .id(member.getId())
                .build();
    }
}
