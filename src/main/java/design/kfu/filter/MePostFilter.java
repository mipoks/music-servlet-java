package design.kfu.filter;

import design.kfu.service.SecurityService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/me")
public class MePostFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (SecurityService.getUser(req) != null) {
            chain.doFilter(req, res);
        } else {
            if (!req.getMethod().equals("POST"))
                chain.doFilter(req, res);
            else
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
