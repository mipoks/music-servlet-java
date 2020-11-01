package design.kfu.servlet.entry;

import design.kfu.service.SecurityService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SecurityService.signOut(req, resp);
        resp.sendRedirect(req.getContextPath() + "/search");
    }

}
