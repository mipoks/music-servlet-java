package design.kfu.service;

import design.kfu.entity.dto.PersonForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ProfileService {
    int INCORRECT_PWD = -2;
    int UNKNOWN_ERROR = -1;
    int NOT_UPDATED = 0;
    int SUCCESS = 1;
    int update(PersonForm personForm, HttpServletRequest req, HttpServletResponse resp);
}
