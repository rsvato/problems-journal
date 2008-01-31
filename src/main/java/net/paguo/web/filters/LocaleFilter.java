package net.paguo.web.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * @version $Id $
 */
public class LocaleFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String oldEncoding = request.getCharacterEncoding();
        if (oldEncoding == null || ! "UTF-8".equals(oldEncoding)){
            request.setCharacterEncoding("UTF-8");
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
