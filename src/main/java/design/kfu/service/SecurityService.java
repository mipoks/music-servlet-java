package design.kfu.service;

import design.kfu.entity.Person;
import design.kfu.helper.entity.PasswordHasher;
import javafx.util.Pair;

import java.util.HashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityService {

    private static HashMap<Integer, Person> users = new HashMap<>();

    public static Person getUser(HttpServletRequest req) {

        Pair<Cookie, Cookie> pair = getIdAuth(req);
        if (pair != null) {
            try {
                Integer id = Integer.parseInt(pair.getKey().getValue());
                String auth = pair.getValue().getValue();
                if (users.get(id) != null && PasswordHasher.check(users.get(id).getEmail(), auth))
                    return users.get(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void signIn(HttpServletRequest req, HttpServletResponse resp, Person person) {
        if (person.getId() != -1) {
            users.put(person.getId(), person);
            try {
                Cookie cookie = new Cookie("id", String.valueOf(person.getId()));
                cookie.setMaxAge(MainService.TIMEOUT_COOKIE);
                resp.addCookie(cookie);
                cookie = new Cookie("auth", PasswordHasher.getSaltedHash(person.getEmail()));
                cookie.setMaxAge(MainService.TIMEOUT_COOKIE);
                resp.addCookie(cookie);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void signOut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Cookie cookie = new Cookie("id", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            cookie = new Cookie("auth", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Pair<Cookie, Cookie> getIdAuth(HttpServletRequest req) {

        Cookie[] cookies = req.getCookies();
        Cookie id = null, auth = null;
        if (cookies != null)
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("auth")) {
                    auth = cookies[i];
                }
                if (cookies[i].getName().equals("id")) {
                    id = cookies[i];
                }
                if (auth != null && id != null)
                    break;
            }
        if (auth != null && id != null) {
            return new Pair<>(id, auth);
        }
        return null;
    }
}