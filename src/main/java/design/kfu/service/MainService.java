package design.kfu.service;

import design.kfu.helper.database.ConnectionGiver;
import design.kfu.helper.database.implementation.ConnectionLocal;
import design.kfu.music.MusicGetter;
import design.kfu.music.implementation.v1.GetZaycevMusic;
import design.kfu.repository.PersonRepository;
import design.kfu.repository.implementation.PersonRepoImpl;

import java.sql.SQLException;

public class MainService {
    public final static int TIMEOUT_REQUEST = 43000;
    public final static int TIMEOUT_SONG = 8000;
    public final static int TIMEOUT_COOKIE = 2592000;

    private PersonRepository personRepository;
    private static MainService mainService;
    private ConnectionGiver connectionGiver;

    private MainService() {
        connectionGiver = new ConnectionLocal();
        personRepository = new PersonRepoImpl(connectionGiver);
    }

    public static MainService getInstance() {
        if (mainService == null)
            synchronized (MainService.class) {
                if (mainService == null)
                    mainService = new MainService();
            }
        return mainService;
    }

    public ConnectionGiver getConnectionGiver() {
        return connectionGiver;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

}
