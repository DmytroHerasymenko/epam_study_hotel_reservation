package ua.study.filter;

import ua.study.command.util.UtilFactory;
import ua.study.command.util.localization.Localization;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by dima on 28.04.17.
 */
public class ErrorMessageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String value = (String) session.getAttribute("error");
        if(value != null){
            Localization localization = UtilFactory.getInstance().getLocalization();
            Locale locale = Locale.forLanguageTag(String.valueOf(session.getAttribute("language")));
            servletRequest.setAttribute("error", localization.getMessage(value, locale));
            session.removeAttribute("error");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
