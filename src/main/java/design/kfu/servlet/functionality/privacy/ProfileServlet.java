package design.kfu.servlet.functionality.privacy;

import design.kfu.entity.dto.PersonForm;
import design.kfu.helper.view.Alert;
import design.kfu.helper.url.URLCoder;
import design.kfu.service.ProfileService;
import design.kfu.service.implementation.ProfileServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static String headSuccess = "Настройки сохранены!";
    private static String bodySuccess = "Изменения вступили в силу";
    private static String bodyDanger = "Изменения не сохранены. Попробуйте позднее";

    private ProfileService profileService;
    private Alert info;

    @Override
    public void init() throws ServletException {
        info = new Alert();
        profileService = new ProfileServiceImpl();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Настройки");
        req.getRequestDispatcher("/resources/templates/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Настройки");

        boolean error = false;

        PersonForm personForm = new PersonForm();

        String sc = req.getParameter("songcount");
        String passwordOld = req.getParameter("passwordold");
        String passwordNew = req.getParameter("passwordnew");
        String newname = URLCoder.decodeURIComponent(req.getParameter("newname"));

        if (!sc.equals("")) {
            try {
                int songCount = Integer.parseInt(sc);
                if (songCount > 0 && songCount < 20) {
                    personForm.setSongCount(songCount);
                } else {
                    info.setBody("Кол-во песен должно быть больше 0, но меньше 20");
                }
            } catch (Exception ex) {
                error = true;
                info.setBody("Пожалуйста, введите число");
            }
        }

        if (!passwordOld.equals("") && !passwordNew.equals("")) {
            if (passwordNew.length() >= 6) {
                personForm.setPassword(passwordOld);
                personForm.setPwd2(passwordNew);
            } else {
                error = true;
                info.setBody("Новый пароль слишком короткий!");
            }
        }

        if (!newname.equals("")) {
            if (newname.length() >= 2) {
                personForm.setName(newname);
            } else {
                error = true;
                info.setBody("Имя слишком короткое");
            }
        }

        int ans = profileService.update(personForm, req, resp);

        if (ans == ProfileService.NOT_UPDATED) {
            error = true;
            info.setBody("Не удалось сохранить");
        }
        if (ans == ProfileService.UNKNOWN_ERROR) {
            error = true;
            info.setBody("Проблемы с сохранением. Попробуйте позже");
        }
        if (ans == ProfileService.INCORRECT_PWD) {
            error = true;
            info.setBody("Старый пароль указан неверно");
        }

        if (error) {
            if (info.getBody() == null)
                info.setBody(bodyDanger);
            info.setHead(Alert.HEAD_DANGER);
            info.setColor(Alert.COLOR_DANGER);
        } else {
            info.setBody(bodySuccess);
            info.setHead(headSuccess);
            info.setColor(Alert.COLOR_SUCCESS);
        }
        req.setAttribute("info", info);
        req.getRequestDispatcher("/resources/templates/profile.jsp").forward(req, resp);
    }
}