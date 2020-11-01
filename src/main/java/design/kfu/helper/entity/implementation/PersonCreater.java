package design.kfu.helper.entity.implementation;

import design.kfu.entity.Person;
import design.kfu.helper.entity.EntityCreater;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonCreater implements EntityCreater {
    public Person create(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        String email = resultSet.getString("password");
        int id = resultSet.getInt("id");
        int songCount = resultSet.getInt("song_count");
        if (songCount == 0)
            songCount = 5;
        Person person = new Person(name, email, password, id, songCount);
        return person;
    }
}
