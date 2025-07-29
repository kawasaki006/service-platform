package com.kawasaki.service.auth_service.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawasaki.service.common.utils.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiResponse apiRes = ApiResponse.error(HttpStatus.FORBIDDEN.value(), "You are not authorized to access this resource!");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(apiRes);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(json);
    }
}
