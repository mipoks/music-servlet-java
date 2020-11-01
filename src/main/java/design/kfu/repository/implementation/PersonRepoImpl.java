package design.kfu.repository.implementation;

import design.kfu.entity.Person;
import design.kfu.helper.database.ConnectionGiver;
import design.kfu.helper.database.JdbcTemplate;
import design.kfu.repository.PersonRepository;
import design.kfu.repository.implementation.database.SQLPersonSaver;

import java.sql.Connection;
import java.util.Collection;

public class PersonRepoImpl implements PersonRepository {

    private SQLPersonSaver sqlPersonSaver;
    private JdbcTemplate jdbcTemplate;

    public PersonRepoImpl(ConnectionGiver connectionGiver) {
        jdbcTemplate = new JdbcTemplate(connectionGiver);
        sqlPersonSaver = new SQLPersonSaver(jdbcTemplate);
    }

    @Override
    public int save(Person person){
        return sqlPersonSaver.insert(person);
    }

    @Override
    public Collection<Person> getAll() {
        return sqlPersonSaver.getAll();
    }

    @Override
    public int update(Person person) {
        return sqlPersonSaver.update(person);
    }

    @Override
    public Person findByEmail(String email) {
        return sqlPersonSaver.findByEmail(email);
    }

    @Override
    public int delete(Person person) {
        return sqlPersonSaver.delete(person);
    }
}
