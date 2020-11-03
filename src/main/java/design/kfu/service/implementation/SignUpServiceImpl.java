package design.kfu.service.implementation;

import design.kfu.entity.dto.PersonForm;
import design.kfu.entity.Person;
import design.kfu.helper.entity.PasswordHasher;
import design.kfu.repository.PersonRepository;
import design.kfu.service.MainService;
import design.kfu.service.SecurityService;
import design.kfu.service.SignInService;
import design.kfu.service.SignUpService;

import java.sql.SQLException;

public class SignUpServiceImpl implements SignUpService {
    private MainService mainService;
    private PersonRepository dbPerson;

    public SignUpServiceImpl() {
        mainService = MainService.getInstance();
        dbPerson = mainService.getPersonRepository();
    }

    @Override
    public int expel(Person person) {
        return dbPerson.delete(person);
    }

    @Override
    public int signUp(PersonForm personForm) {
        int errors = personForm.isValid();
        if (errors <= 0) {
            return errors;
        } else {
                Person person = null;
                try {
                    person = new Person(personForm.getName(), personForm.getEmail(),
                            PasswordHasher.getSaltedHash(personForm.getPassword()));
                } catch (Exception e) {
                    e.printStackTrace();
                    return UNKNOWN_ERROR;
                }

                int ans = dbPerson.save(person);
                if (ans == dbPerson.NOT_CHANGED) {
                    return UNKNOWN_ERROR;
                }
                if (ans == dbPerson.NOT_EXECUTED) {
                    return ALREADY_EXIST;
                }
                if (ans == dbPerson.NOT_DESIGNATED) {
                    return REGISTERED_WITH_ERROR;
                }
        }
        return SUCCESS;
    }
}
