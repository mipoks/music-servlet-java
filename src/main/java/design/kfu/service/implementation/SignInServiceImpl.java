package design.kfu.service.implementation;

import design.kfu.entity.dto.PersonForm;
import design.kfu.entity.Person;
import design.kfu.helper.entity.PasswordHasher;
import design.kfu.repository.PersonRepository;
import design.kfu.service.MainService;
import design.kfu.service.SecurityService;
import design.kfu.service.SignInService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInServiceImpl implements SignInService {
    private MainService mainService;
    private PersonRepository dbPerson;

    public SignInServiceImpl(){
        mainService = MainService.getInstance();
        dbPerson = mainService.getPersonRepository();
    }

    @Override
    public int signIn(PersonForm personForm, HttpServletRequest req, HttpServletResponse resp) {
        Person person = dbPerson.findByEmail(personForm.getEmail());

        try {
            if (person == null || !PasswordHasher.check(personForm.getPassword(), person.getPassword())) {
                return INCORRECT_FORM;
            } else {
                SecurityService.signIn(req, resp, person);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return UNKNOWN_ERROR;
        }
        return person.getId();
    }
}
