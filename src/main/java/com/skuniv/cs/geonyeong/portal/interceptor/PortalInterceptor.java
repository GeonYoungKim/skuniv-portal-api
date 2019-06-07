package com.skuniv.cs.geonyeong.portal.interceptor;

import static com.skuniv.cs.geonyeong.portal.constant.PortalConstant.PROFESSOR_ID_KEY;
import static com.skuniv.cs.geonyeong.portal.constant.PortalConstant.STUDENT_ID_KEY;

import com.skuniv.cs.geonyeong.portal.enums.AccountType;
import com.skuniv.cs.geonyeong.portal.exception.MissingAccountTypeException;
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException;
import com.skuniv.cs.geonyeong.portal.exception.TokenInvalidException;
import com.skuniv.cs.geonyeong.portal.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
@Component
@RequiredArgsConstructor
public class PortalInterceptor extends HandlerInterceptorAdapter {

    private final JwtService jwtService;

    private final String HEADER_TOKEN_KEY = "Token";
    private final String HEADER_ACCOUNT_TYPE_KEY = "AccountType";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        log.info("preHandle");
        log.info("path => {}", request.getRequestURI());
        try {
            AccountType accountType = AccountType
                .valueOf(request.getHeader(HEADER_ACCOUNT_TYPE_KEY));
            boolean isCheck = false;
            String jwt;
            switch (accountType) {
                case PROFESSOR:
                    log.info("accountType is PROFESSOR");
                    jwt = request.getHeader(HEADER_TOKEN_KEY);
                    isCheck = jwtService.checkProcessor(jwt);
                    checkAuthentication(isCheck, request, PROFESSOR_ID_KEY,
                        jwtService.getJwtId(jwt));
                    break;
                case STUDENT:
                    jwt = request.getHeader(HEADER_TOKEN_KEY);
                    isCheck = jwtService.checkStudent(jwt);
                    log.info("check => {}", isCheck);
                    checkAuthentication(isCheck, request, STUDENT_ID_KEY,
                        jwtService.getJwtId(jwt));
                    break;
            }
        } catch (ExpiredJwtException e) {
            log.error("토큰이 만료되었음.");
            throw new TokenExpireException();
        } catch (NullPointerException e) {
            log.error("Missing Account-Type");
            throw new MissingAccountTypeException();
        }
        log.info("pass interceptor");
        return true;
    }

    private void checkAuthentication(boolean isCheck, HttpServletRequest request,
        String attributeKey, String attributeValue) {
        if (isCheck) {
            request.setAttribute(attributeKey, attributeValue);
        } else {
            throw new TokenInvalidException();
        }
    }
}
