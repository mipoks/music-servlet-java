package design.kfu.repository.implementation.database;

import design.kfu.entity.Person;
import design.kfu.entity.Request;
import design.kfu.entity.Song;
import design.kfu.helper.database.JdbcTemplate;
import design.kfu.helper.database.RowMapper;
import design.kfu.helper.database.implementation.RowMapperImpl;
import design.kfu.helper.entity.implementation.SongCreater;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static design.kfu.repository.States.NOT_EXECUTED;

public class SQLSongSaver {

    //insert song into person_songs
    //language=SQL
    private final static String SQL_SONG_INSERT_P = "INSERT INTO person_songs(person_id, song_id) VALUES (?,?)";

    //select songs by person
    //language=SQL
    private final static String SQL_SONG_SELECT_P = "WITH temp as (SELECT * FROM songs JOIN person_songs ON song_id = id) SELECT name, url, original_url, id, updated FROM temp WHERE person_id=(?)";

    //select songs by request
    //language=SQL
    private final static String SQL_SONG_SELECT_R = "SELECT name, url, original_url, id, updated FROM songs WHERE id=ANY(?)";

    //update song
    //language=SQL
    private final static String SQL_SONG_UPDATE_P = "UPDATE persons_songs SET song_id=(?) WHERE person_id=(?)";

    //delete song from person_songs
    //language=SQL
    private final static String SQL_SONG_DELETE_P = "DELETE FROM person_songs WHERE person_id=(?) AND song_id=(?)";

    //insert song to songs
    //language=SQL
    private final static String SQL_SONG_INSERT_S = "INSERT INTO songs(name, url, id, original_url, updated) VALUES (?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET updated=(?), url=(?)";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<Song> rowMapper;

    public SQLSongSaver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        rowMapper = new RowMapperImpl<>(new SongCreater());
    }

    public Collection<Song> get(Person person) {
        return jdbcTemplate.query(SQL_SONG_SELECT_P, rowMapper, person.getId());
    }

    public Collection<Song> get(Request request) {
        Collection<Song> temp = request.getSongs();
        int[] id_songs = new int[temp.size()];
        int i = 0;
        for (Song song : temp) {
            id_songs[i] = song.getId();
            i++;
        }
        return jdbcTemplate.query(SQL_SONG_SELECT_R, rowMapper, id_songs);
    }

    public int update(Collection<Song> collection, Person person) {
        Iterator it = collection.iterator();
        Song song;
        int updated = 0;
        while (it.hasNext()) {
            song = (Song) it.next();
            jdbcTemplate.query(SQL_SONG_UPDATE_P, rowMapper, song.getId(), person.getId());
            updated += rowMapper.getResult();
        }
        return updated;
    }

    public int delete(Collection<Song> collection, Person person) {
        Iterator it = collection.iterator();
        Song song;
        int deleted = 0;
        while (it.hasNext()) {
            song = (Song) it.next();
            jdbcTemplate.query(SQL_SONG_DELETE_P, rowMapper, person.getId(), song.getId());
            deleted += rowMapper.getResult();
        }
        return deleted;
    }

    public int insert(Collection<Song> collection, Person person) {
        Iterator it = collection.iterator();
        int inserted = 0;
        while (it.hasNext()) {
            Song song = (Song) it.next();
            jdbcTemplate.query(SQL_SONG_INSERT_P, rowMapper, person.getId(), song.getId());
            inserted += rowMapper.getResult();
        }
        return inserted;
    }

    public int insert(Collection<Song> collection) {
        Iterator it = collection.iterator();
        int inserted = 0;
        while (it.hasNext()) {
            Song song = (Song) it.next();
            jdbcTemplate.query(SQL_SONG_INSERT_S, rowMapper, song.getSongName(), song.getUrl(),
                    song.getId(), song.getOriginalUrl(), song.getTime(), song.getTime(), song.getUrl());
            inserted += rowMapper.getResult();
        }
        return inserted;
    }

    public int delete(Collection<Song> collection) {
        return 0;
    }

    public int update(Collection<Song> collection) {
        return 0;
    }

    public Collection<Song> getAll() {
        return null;
    }

}
