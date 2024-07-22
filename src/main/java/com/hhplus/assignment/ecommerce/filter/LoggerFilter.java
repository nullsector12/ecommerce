package com.hhplus.assignment.ecommerce.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;


@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);
        log.info("INIT URI: {}", requestWrapper.getRequestURI());

        filterChain.doFilter(requestWrapper, responseWrapper);

        Enumeration<String> headerNames = requestWrapper.getHeaderNames();
        StringBuilder headers = new StringBuilder();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.append(headerName).append(": ").append(requestWrapper.getHeader(headerName)).append("\n");
        }

        log.info(">>>>>>>>> REQUEST URI: {}, method: {},  header: {}",requestWrapper.getRequestURI(), requestWrapper.getMethod(), headers);
        log.info(">>>>>>>>> REQUEST BODY: {}", new String(requestWrapper.getContentAsByteArray()));
        log.info("<<<<<<<<< RESPONSE BODY: {}", new String(responseWrapper.getContentAsByteArray()));

        responseWrapper.copyBodyToResponse();
    }
}
