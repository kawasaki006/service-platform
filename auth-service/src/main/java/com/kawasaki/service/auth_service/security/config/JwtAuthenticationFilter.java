package com.kawasaki.service.auth_service.security.config;

import com.kawasaki.service.auth_service.dto.UserDTO;
import com.kawasaki.service.common.constants.AuthConstants;
import com.kawasaki.service.common.security.UserDetailsImpl;
import com.kawasaki.service.common.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            // currently not needed at all; also did not account for INTERNAL TOKEN; assumes user token
            Claims claims = JWTUtils.verifyToken(token);

            UserDetailsImpl userDetails = new UserDetailsImpl();
            userDetails.setId(Long.valueOf(claims.get(AuthConstants.ID, String.class)));
            userDetails.setEmail(claims.get(AuthConstants.EMAIL, String.class));

            Collection<GrantedAuthority> authorities = new ArrayList<>();
            List<String> roles = claims.get(AuthConstants.ROLES, List.class);
            if (roles != null) {
                roles.forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(AuthConstants.ROLE_PREFIX + role));
                });
            }
            userDetails.setAuthorities(authorities);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    authorities
            );
            // will get overwritten in controller
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            // continue even with invalid token
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
