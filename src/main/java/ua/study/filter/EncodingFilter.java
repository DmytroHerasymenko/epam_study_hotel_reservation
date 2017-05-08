package ua.study.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by dima on 27.04.17.
 */
public class EncodingFilter implements Filter {

    private static final String FILTERABLE_CONTENT_TYPE="application/x-www-form-urlencoded";
    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final String ENCODING_INIT_PARAM_NAME = "encoding";
    private static final String CONTENT_TYPE = "text/html;charset=utf-8";

    private String encoding;

    public void destroy(){
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException {
        String contentType = req.getContentType();
        if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE))
            req.setCharacterEncoding(encoding);
        resp.setContentType(CONTENT_TYPE);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException{
        encoding = config.getInitParameter(ENCODING_INIT_PARAM_NAME);
        if (encoding == null)
            encoding = ENCODING_DEFAULT;
    }
}
