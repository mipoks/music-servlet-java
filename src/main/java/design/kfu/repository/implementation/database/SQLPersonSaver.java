package design.kfu.repository.implementation.database;

import design.kfu.entity.Person;
import design.kfu.helper.database.JdbcTemplate;
import design.kfu.helper.database.RowMapper;
import design.kfu.helper.database.implementation.RowMapperImpl;
import design.kfu.helper.entity.implementation.PersonCreater;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static design.kfu.repository.States.NOT_EXECUTED;

public class SQLPersonSaver {

    //insert person
    //language=SQL
    private final static String SQL_PERSON_INSERT = "INSERT INTO persons(email, name, password) VALUES (?,?,?)";

    //select person
    //language=SQL
    private final static String SQL_PERSON_SELECT = "SELECT * FROM persons WHERE email=(?)";

    //update person
    //language=SQL
    private final static String SQL_PERSON_UPDATE = "UPDATE persons SET name=(?), password=(?), song_count=(?) WHERE id=(?)";


    private JdbcTemplate jdbcTemplate;
    private RowMapper<Person> rowMapper;

    public SQLPersonSaver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        rowMapper = new RowMapperImpl<>(new PersonCreater());
    }

    public Collection<Person> getAll() {
        return null;
    }

    public int insert(Person person) {
        jdbcTemplate.query(SQL_PERSON_INSERT, rowMapper,
                person.getEmail(), person.getName(), person.getPassword());
        return rowMapper.getResult();
    }

    public int delete(Person person) {
        return 0;
    }

    public Person findByEmail(String email) {
        Collection<Person> collection = jdbcTemplate.query(SQL_PERSON_SELECT, rowMapper, email);
        Iterator it = collection.iterator();
        if (it.hasNext())
            return (Person) it.next();
        return null;
    }

    public int update(Person person) {
        jdbcTemplate.query(SQL_PERSON_UPDATE, rowMapper,
                person.getName(), person.getPassword(),
                person.getSongCount(), person.getId());
        return rowMapper.getResult();
    }
}
