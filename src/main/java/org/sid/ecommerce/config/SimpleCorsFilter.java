package org.sid.ecommerce.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {
    @Value("${app.client.url}")
   private String clientAppUrl;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String originHeader = request.getHeader("Origin");

            response.setHeader("Access-Control-Allow-Origin", originHeader);
            response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS, GET, DELETE, PUT");
            response.setHeader("Access-Control-Allow-Headers", "*");
            response.setHeader("Access-Control-Allow-Max-Age", "3600");

            // Handle preflight requests
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            }
           else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

    }

}
