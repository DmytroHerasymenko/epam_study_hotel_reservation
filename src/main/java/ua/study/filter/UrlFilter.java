package ua.study.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Created by dima on 09.04.17.
 */
public class UrlFilter implements Filter {
    private final Logger LOGGER = LogManager.getLogger(UrlFilter.class.getName());
    private final Set<String> localAddresses = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/filter.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        localAddresses.add(properties.getProperty("url.start"));
        localAddresses.add(properties.getProperty("url.registr"));
        localAddresses.add(properties.getProperty("url.registr_handler"));
        localAddresses.add(properties.getProperty("url.sign_in"));
        localAddresses.add(properties.getProperty("url.sign_in_handler"));
        localAddresses.add(properties.getProperty("url.dates"));
        localAddresses.add(properties.getProperty("url.dates_handler"));
        localAddresses.add(properties.getProperty("url.reserv"));
        localAddresses.add(properties.getProperty("url.reserv_handler"));
        localAddresses.add(properties.getProperty("url.bill"));
        localAddresses.add(properties.getProperty("url.bill_handler"));
        localAddresses.add(properties.getProperty("url.my_reservations"));
        localAddresses.add(properties.getProperty("url.confirmation"));
        localAddresses.add(properties.getProperty("url.image_header"));
        localAddresses.add(properties.getProperty("url.image_line"));
        localAddresses.add(properties.getProperty("url.index"));
        localAddresses.add(properties.getProperty("url.contacts"));
        localAddresses.add(properties.getProperty("url.news"));
        localAddresses.add(properties.getProperty("url.about"));
        localAddresses.add(properties.getProperty("a"));
    }

        @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (localAddresses.contains(((HttpServletRequest) servletRequest).getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("/WEB-INF/jsp/404.jsp")
                    .include(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
