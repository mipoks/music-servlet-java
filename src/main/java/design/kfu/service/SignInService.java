package design.kfu.service;

import design.kfu.entity.dto.PersonForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SignInService {
    int NOT_FOUND_PERSON = -2;
    int INCORRECT_FORM = -1;
    int UNKNOWN_ERROR = 0;
    int signIn(PersonForm personForm, HttpServletRequest req, HttpServletResponse resp);
}
