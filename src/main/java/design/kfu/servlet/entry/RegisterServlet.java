package design.kfu.servlet.entry;

import design.kfu.helper.view.Alert;
import design.kfu.entity.dto.PersonForm;
import design.kfu.helper.url.URLCoder;
import design.kfu.service.SignUpService;
import design.kfu.service.implementation.SignUpServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private Alert info;
    private static String headSuccess = "Регистрация прошла успешно!";
    private static String successRegistration = "Теперь Вы можете войти в аккаунт";

    private PersonForm personForm;
    private SignUpService signUpService;

    @Override
    public void init() throws ServletException {
        info = new Alert();
        personForm = new PersonForm();
        signUpService = new SignUpServiceImpl();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Регистрация");
        req.getRequestDispatcher("/resources/templates/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Регистрация");
        String name = URLCoder.decodeURIComponent(req.getParameter("name"));
        String email = req.getParameter("email").toLowerCase();
        String pwd1 = req.getParameter("pwd1");
        String pwd2 = req.getParameter("pwd2");
        String agree = req.getParameter("agree");
        personForm.setEmail(email);
        personForm.setName(name);
        personForm.setPassword(pwd1);
        personForm.setPwd2(pwd2);
        personForm.setAgree(agree);

        boolean err = false;
        int ans = signUpService.signUp(personForm);

        if (ans == SignUpService.ALREADY_EXIST) {
            info.setBody("Пользователь с таким Email уже существует");
            err = true;
        }
        if (ans == SignUpService.AGREEMENT_FALSE) {
            info.setBody("Согласитесь с правилами сайта");
            err = true;
        }
        if (ans == SignUpService.EMAIL_INCORRECT) {
            info.setBody("Введите корректный email");
            err = true;
        }
        if (ans == SignUpService.PWD_SIZE_LONG || ans == SignUpService.PWD_SIZE_SHORT) {
            info.setBody("Пароль должен быть больше 6, но меньше 30 символов");
            err = true;
        }
        if (ans == SignUpService.PWDS_NOT_EQUALS) {
            info.setBody("Пароли различаются");
            err = true;
        }
        if (ans == SignUpService.EMPTY_FIELD) {
            info.setBody("Заполните все поля");
            err = true;
        }
        if (ans == SignUpService.UNKNOWN_ERROR) {
            info.setBody("Неизвестная ошибка");
            err = true;
        }
        if (ans == SignUpService.NAME_SIZE_ERROR) {
            info.setBody("Имя должно быть больше 1, но меньше 30 символов");
            err = true;
        }
        if (ans == SignUpService.REGISTERED_WITH_ERROR || ans == SignUpService.SUCCESS) {
            info.setBody(successRegistration);
        }
        if (err) {
            info.setColor(Alert.COLOR_DANGER);
            info.setHead(Alert.HEAD_DANGER);
        } else {
            info.setColor(Alert.COLOR_SUCCESS);
            info.setHead(headSuccess);
        }
        req.setAttribute("info", info);
        req.setAttribute("personForm", personForm);
        req.getRequestDispatcher("/resources/templates/register.jsp").forward(req, resp);
    }
}
