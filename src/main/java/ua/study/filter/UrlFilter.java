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
    private final Properties properties = new Properties();
    private final Logger LOGGER = LogManager.getLogger(UrlFilter.class.getName());
    private final Set<String> localAddresses = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        try {
            properties.load(getClass().getResourceAsStream("/filter.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        localAddresses.add(properties.getProperty("url.index"));
        localAddresses.add(properties.getProperty("url.registr"));
        //localAddresses.add(properties.getProperty("url.registr_handler"));
        localAddresses.add(properties.getProperty("url.sign_in"));
        //localAddresses.add(properties.getProperty("url.sign_in_handler"));
        localAddresses.add(properties.getProperty("url.dates"));
        //localAddresses.add(properties.getProperty("url.dates_handler"));
        localAddresses.add(properties.getProperty("url.reserv"));
        //localAddresses.add(properties.getProperty("url.reserv_handler"));
        localAddresses.add(properties.getProperty("url.bill"));
        localAddresses.add(properties.getProperty("url.bill_handler"));
        localAddresses.add(properties.getProperty("url.my_reservations"));
    }

        @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (localAddresses.contains(((HttpServletRequest) servletRequest).getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("/WEB-INF/jsp/bad_request.jsp")
                    .include(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
