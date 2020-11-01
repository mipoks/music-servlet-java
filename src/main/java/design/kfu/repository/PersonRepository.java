package design.kfu.repository;

import design.kfu.entity.Person;

import java.util.Collection;

public interface PersonRepository extends States{
    int save(Person person);
    Collection<Person> getAll();
    int update(Person person);
    Person findByEmail(String email);
    int delete(Person person);
}
