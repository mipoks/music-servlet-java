package design.kfu.servlet.entry;

import design.kfu.entity.dto.PersonForm;
import design.kfu.helper.view.Alert;
import design.kfu.service.SignInService;
import design.kfu.service.implementation.SignInServiceImpl;
import design.kfu.service.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private SignInService signInService;
    private Alert info;
    private String referer;
    private boolean signed;

    @Override
    public void init() throws ServletException {
        signInService = new SignInServiceImpl();
        info = new Alert();
        super.init();
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("title", "Войти");
        referer = referer == null ? "" : referer;
        referer = referer.contains("/login") ? null : referer;

        if (SecurityService.getUser(req) != null || signed) {
            if (referer != null && !referer.equals("")) {
                resp.sendRedirect(referer);
            } else {
                resp.sendRedirect(req.getContextPath() + "/me");
            }
        } else {
            req.getRequestDispatcher("/resources/templates/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        signed = false;
        referer = req.getHeader("referer");
        redirect(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email").toLowerCase();
        String password = req.getParameter("password");
        PersonForm personForm = new PersonForm(email, password);

        String error = "false";
        int ans = signInService.signIn(personForm, req, resp);

        signed = false;
        if (ans == SignInService.UNKNOWN_ERROR) {
            error = "Неизвестная ошибка";
        }
        if (ans == SignInService.INCORRECT_FORM || ans == SignInService.NOT_FOUND_PERSON) {
            error = "Неверный логин или пароль";
        }
        if (ans > 0)
            signed = true;

        if (!error.equals("false")) {
            info.setBody(error);
            info.setColor(Alert.COLOR_DANGER);
            info.setHead(Alert.HEAD_DANGER);
            req.setAttribute("info", info);
        }
        redirect(req, resp);
    }
}
