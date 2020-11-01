package design.kfu.repository.implementation;

import design.kfu.entity.Person;
import design.kfu.entity.Request;
import design.kfu.entity.Song;
import design.kfu.helper.database.ConnectionGiver;
import design.kfu.helper.database.JdbcTemplate;
import design.kfu.repository.SongRepository;
import design.kfu.repository.implementation.database.SQLSongSaver;

import java.sql.Connection;
import java.util.*;


public class SongRepoImpl implements SongRepository {

    private SQLSongSaver sqlSongSaver;
    private Person person;
    private JdbcTemplate jdbcTemplate;

    public SongRepoImpl(Person person, ConnectionGiver connectionGiver) {
        jdbcTemplate = new JdbcTemplate(connectionGiver);
        sqlSongSaver = new SQLSongSaver(jdbcTemplate);
        this.person = person;
    }

    public SongRepoImpl(ConnectionGiver connectionGiver) {
        jdbcTemplate = new JdbcTemplate(connectionGiver);
        sqlSongSaver = new SQLSongSaver(jdbcTemplate);
    }

    @Override
    public int save(Song song) {
        Collection<Song> collection = new ArrayList<>();
        collection.add(song);
        if (person != null) {
            return sqlSongSaver.insert(collection, person);
        }
        return sqlSongSaver.insert(collection);
    }

    @Override
    public Collection<Song> getAll() {
        return sqlSongSaver.getAll();
    }

    @Override
    public int delete(Song song) {
        Collection<Song> collection = new ArrayList<>();
        collection.add(song);
        if (person != null)
            return sqlSongSaver.delete(collection, person);
        return NOT_EXECUTED;
    }

    @Override
    public int update(Song song) {
        Collection<Song> collection = new ArrayList<>();
        collection.add(song);
        if (person != null)
            sqlSongSaver.update(collection, person);
        return sqlSongSaver.update(collection);
    }

    @Override
    public Collection<Song> getByRequest(Request request) {
        return sqlSongSaver.get(request);
    }

    @Override
    public Collection<Song> getByPerson(Person person) {
        return sqlSongSaver.get(person);
    }

}
