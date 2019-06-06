package com.skuniv.cs.geonyeong.portal.filter;


import com.skuniv.cs.geonyeong.portal.enums.AccountType;
import com.skuniv.cs.geonyeong.portal.exception.MissingAccountTypeException;
import com.skuniv.cs.geonyeong.portal.exception.TokenExpireException;
import com.skuniv.cs.geonyeong.portal.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class PortalAuthenticationFilter extends AbstractRequestLoggingFilter {
    private final JwtService jwtService;

    private final String HEADER_TOKEN_KEY = "Token";
    private final String HEADER_ACCOUNT_TYPE_KEY = "Account-Type";

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        try {
           AccountType accountType = AccountType.valueOf(request.getHeader(HEADER_ACCOUNT_TYPE_KEY));
            switch (accountType) {
                case PROFESSOR:
                    jwtService.checkProcessor(request.getHeader(HEADER_TOKEN_KEY));
                    break;
                case STUDENT:
                    jwtService.checkStudent(request.getHeader(HEADER_TOKEN_KEY));
                    break;
            }
        } catch (ExpiredJwtException e) {
            log.error("토큰이 만료되었음.");
            throw new TokenExpireException();
        } catch (IllegalArgumentException e) {
            log.error("Missing Account-Type");
            throw new MissingAccountTypeException();
        }
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
    }
}
