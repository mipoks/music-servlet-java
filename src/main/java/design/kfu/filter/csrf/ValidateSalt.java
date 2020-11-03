package design.kfu.filter.csrf;

import com.google.common.cache.Cache;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/login", "/register", "/me", "/profile"})
public class ValidateSalt extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Assume its HTTP
        HttpServletRequest httpReq = (HttpServletRequest) request;
        if (!httpReq.getMethod().equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // Get the salt sent with the request
        String salt = (String) httpReq.getParameter("csrfPreventionSalt");

        // Validate that the salt is in the cache
        Cache<String, Boolean> csrfPreventionSaltCache = (Cache<String, Boolean>)
                httpReq.getSession().getAttribute("csrfPreventionSaltCache");


        String requestURL = request.getRequestURL().toString();
        if (csrfPreventionSaltCache != null &&
                salt != null &&
                csrfPreventionSaltCache.getIfPresent(salt) != null){

            // If the salt is in the cache, we move on
            if (!requestURL.contains("/me"))
            csrfPreventionSaltCache.invalidate(salt);
            chain.doFilter(request, response);
        } else {
            // Otherwise we throw an exception aborting the request flow
            if (requestURL.contains("/me")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            if (requestURL.contains("/login") || requestURL.contains("/register") || requestURL.contains("/profile")) {
                response.sendRedirect(requestURL);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}