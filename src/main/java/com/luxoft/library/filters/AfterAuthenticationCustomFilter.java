package com.luxoft.library.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class AfterAuthenticationCustomFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(AfterAuthenticationCustomFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        logger.info("SOMEONE HAS LOGGED IN");
        filterChain.doFilter(request, response);
    }
}
