package com.bookshelf.global.config;

import com.bookshelf.member.domain.Session;
import com.bookshelf.member.dto.request.MemberLogout;
import com.bookshelf.member.exception.UnauthorizedException;
import com.bookshelf.member.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class LogoutResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberLogout.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception {

        HttpServletRequest httpServletRequest = getHttpServletRequest(webRequest);
        Cookie[] cookies = getCookies(httpServletRequest);
        Session session = getSession(cookies);
        cookies[0].setMaxAge(0);

        return MemberLogout.builder()
                .id(session.getMember().getId())
                .build();
    }

    private static HttpServletRequest getHttpServletRequest(NativeWebRequest webRequest) {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (httpServletRequest == null) {
            throw new UnauthorizedException();
        }
        return httpServletRequest;
    }

    private static Cookie[] getCookies(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            throw new UnauthorizedException();
        }
        return cookies;
    }

    private Session getSession(Cookie[] cookies) {
        String accessToken = cookies[0].getValue();
        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(UnauthorizedException::new);
        return session;
    }
}
