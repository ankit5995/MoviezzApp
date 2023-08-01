package com.niit.bej.UserMovieService.filter;

import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
//do filter
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String authHeader = httpServletRequest.getHeader("Authorization");//token is given
        ServletOutputStream sw = httpServletResponse.getOutputStream();
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sw.println("Token Missing");
            sw.close();
        } else {
            String jwt = authHeader.substring("Bearer ".length());
            String emailId = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(jwt).getBody().getSubject();
            httpServletRequest.setAttribute("emailId", emailId);
        }
        chain.doFilter(request, response);

    }
}
