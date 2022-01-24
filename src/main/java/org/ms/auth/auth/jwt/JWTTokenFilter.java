package org.ms.auth.auth.jwt;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@AllArgsConstructor
public class JWTTokenFilter extends GenericFilterBean {

    @Autowired
    private JWTTokernProvider jwtTokernProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokernProvider.resolveToken((HttpServletRequest) servletRequest);
        if(token != null && jwtTokernProvider.validateToken(token)){
            Authentication authentication = jwtTokernProvider.getAuthentication(token);
            if (authentication.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
