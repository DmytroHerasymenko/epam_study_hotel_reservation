package ua.study.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.study.command.impl.LoginCommand;

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
    private final Logger LOGGER = LogManager.getLogger(LoginCommand.class.getName());
    private final Set<String> localAddresses = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) {
        try {
            properties.load(getClass().getResourceAsStream("/req.properties"));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        localAddresses.add(properties.getProperty("url.index"));
        localAddresses.add(properties.getProperty("url.login"));
        localAddresses.add(properties.getProperty("url.registr"));
        localAddresses.add(properties.getProperty("url.reserv"));
        localAddresses.add(properties.getProperty("url.bill"));
        localAddresses.add(properties.getProperty("url.login_h"));
        localAddresses.add(properties.getProperty("url.reserv_h"));
        localAddresses.add(properties.getProperty("url.registr_h"));
    }

        @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (localAddresses.contains(((HttpServletRequest) servletRequest).getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            RequestDispatcher dispatcher =
                    servletRequest.getRequestDispatcher(properties.getProperty("req.bad"));
            dispatcher.forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
