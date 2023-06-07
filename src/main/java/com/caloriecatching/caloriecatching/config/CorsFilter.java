package com.caloriecatching.caloriecatching.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {


    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow_Origin", "http://localhost:5000");
        response.setHeader("Access-Contorl-Allow-Credentials", "true");
        response.setHeader("Access-Contorl-Allow-Methods", "*");
        response.setHeader("Access-Contorl-Max-Age", "3600");
        response.setHeader("Access-Contorl-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
