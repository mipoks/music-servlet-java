package design.kfu.service.implementation;

import design.kfu.entity.dto.PersonForm;
import design.kfu.entity.Person;
import design.kfu.helper.entity.PasswordHasher;
import design.kfu.repository.PersonRepository;
import design.kfu.service.MainService;
import design.kfu.service.ProfileService;
import design.kfu.service.SecurityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ProfileServiceImpl implements ProfileService {
    private MainService mainService;
    private PersonRepository dbPerson;

    public ProfileServiceImpl() {
        mainService = MainService.getInstance();
        dbPerson = mainService.getPersonRepository();
    }

    @Override
    public int update(PersonForm personForm, HttpServletRequest req, HttpServletResponse resp) {
        Person person = SecurityService.getUser(req);
        if (personForm.getSongCount() > 0) {
            person.setSongCount(personForm.getSongCount());
        }

        try {
            if (personForm.getPassword() != null && personForm.getPwd2() != null) {
                if (PasswordHasher.check(personForm.getPassword(), person.getPassword())) {
                    person.setPassword(PasswordHasher.getSaltedHash(personForm.getPwd2()));
                } else {
                    return INCORRECT_PWD;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return UNKNOWN_ERROR;
        }

        if (personForm.getName() != null) {
            person.setName(personForm.getName());
        }

        int ans = dbPerson.update(person);
        if (ans == dbPerson.NOT_CHANGED) {
            return NOT_UPDATED;
        }
        if (ans == dbPerson.NOT_EXECUTED) {
            return UNKNOWN_ERROR;
        }
        if (ans > 0) {
            return SUCCESS;
        }
        return UNKNOWN_ERROR;
    }
}
