package com.fikfit.api.security;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {
    private static final String JWT_SECRET = "fikfit_secret_key_12345678901234567890123456789012";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(JWT_SECRET.getBytes())
                        .parseClaimsJws(token)
                        .getBody();
                request.setAttribute("claims", claims);
                return true;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired token");
                return false;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Missing or invalid Authorization header");
        return false;
    }
}

