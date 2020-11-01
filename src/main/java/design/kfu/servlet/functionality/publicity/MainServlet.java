package design.kfu.servlet.functionality.publicity;

import design.kfu.service.SecurityService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (SecurityService.getUser(req) != null) {
            resp.sendRedirect(req.getContextPath() + "/me");
        } else {
            resp.sendRedirect(req.getContextPath() + "/search");
        }
    }
}
