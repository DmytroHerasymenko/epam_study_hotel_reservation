package ua.study.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dima on 09.04.17.
 */
public class UrlFilter implements Filter {
    private final Set<String> localAddresses = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        localAddresses.add("/");
        localAddresses.add("/service/login");
        localAddresses.add("/service/registration");
        localAddresses.add("/service/reservation");
        localAddresses.add("/service/bill");
        localAddresses.add("/service/login_handler");
        localAddresses.add("/service/reservation_handler");
        localAddresses.add("/service/registration_handler");
    }

        @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (localAddresses.contains(((HttpServletRequest) servletRequest).getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/WEB-INF/jsp/bad_request.jsp");
            dispatcher.forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
