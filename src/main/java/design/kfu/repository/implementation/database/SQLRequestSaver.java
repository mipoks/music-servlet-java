package design.kfu.repository.implementation.database;

import design.kfu.entity.Request;
import design.kfu.entity.Song;
import design.kfu.helper.database.JdbcTemplate;
import design.kfu.helper.database.RowMapper;
import design.kfu.helper.database.implementation.RowMapperImpl;
import design.kfu.helper.entity.implementation.RequestCreater;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class SQLRequestSaver {

    //insert or update request_info table
    //language=SQL
    private final static String SQL_REQUEST_INFO_UPDATE = "INSERT INTO request_info (request_id) VALUES(?) ON CONFLICT(request_id)" +
            " DO UPDATE SET requested_times = request_info.requested_times + 1, last_request_time = NOW()";

    //insert or update requests table
    //language=SQL
    private final static String SQL_REQUEST_INSERT = "INSERT INTO requests(req_name, time, songs, length) VALUES (?,?,to_json(?::json),?) ON CONFLICT(req_name) DO " +
            "UPDATE SET time=(?), songs=(to_json(?::json)), length=(?)";

    //select request from requests table
    //language=SQL
    private final static String SQL_REQUEST_SELECT = "SELECT * FROM requests WHERE req_name = (?)";

    //delete request from request table
    //language=SQL
    private final static String SQL_REQUEST_DELETE = "DELETE FROM requests WHERE id=(?)";

    //delete request_info from request_info table
    //language=SQL
    private final static String SQL_REQUEST_DELETE_INFO = "DELETE FROM request_info WHERE id=(?)";

    //update request
    //language=SQL
    private final static String SQL_REQUEST_UPDATE = "UPDATE requests SET req_name=(?), length=(?), songs=(?) WHERE id=(?)";


    private JdbcTemplate jdbcTemplate;
    private RowMapper<Request> rowMapper;

    public SQLRequestSaver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        rowMapper = new RowMapperImpl<>(new RequestCreater());
    }

    private int updateInfo(Request request) {
        jdbcTemplate.query(SQL_REQUEST_INFO_UPDATE, rowMapper, request.getId());
        return rowMapper.getResult();
    }

    private int deleteInfo(Request request) {
        jdbcTemplate.query(SQL_REQUEST_DELETE_INFO, rowMapper, request.getId());
        return rowMapper.getResult();
    }

    public int insert(Collection<Request> collection) {
        Iterator it = collection.iterator();
        int inserted = 0;
        while (it.hasNext()) {
            Request request = (Request) it.next();
            Collection<Song> songs = request.getSongs();
            JSONObject jsonObject = new JSONObject();
            for (Song song : songs) {
                jsonObject.put(String.valueOf(song.getId()), "T");
            }

            String json = jsonObject.toString();
            jdbcTemplate.query(SQL_REQUEST_INSERT, rowMapper,
                    request.getSearch(), new Date().getTime() / 1000,
                    json, songs.size(), new Date().getTime() / 1000, json, songs.size());
            inserted += rowMapper.getResult();
        }
        return inserted;
    }

    public Request get(String search) {
        Collection<Request> collection = jdbcTemplate.query(SQL_REQUEST_SELECT, rowMapper, search);
        if (collection == null)
            return null;
        Iterator it = collection.iterator();
        if (it.hasNext()) {
            Request request = (Request) it.next();
            updateInfo(request);
            return request;
        }
        return null;
    }

    public int delete(Collection<Request> collection) {
        Iterator it = collection.iterator();
        int deleted = 0;
        while (it.hasNext()) {
            Request request = (Request) it.next();
            jdbcTemplate.connect();
            deleteInfo(request);
            jdbcTemplate.query(SQL_REQUEST_DELETE, rowMapper, request.getId());
            deleted += rowMapper.getResult();
            jdbcTemplate.commit();
        }
        return deleted;
    }

    public int update(Collection<Request> collection) {
        Iterator it = collection.iterator();
        int updated = 0;
        while (it.hasNext()) {
            Request request = (Request) it.next();
            jdbcTemplate.query(SQL_REQUEST_UPDATE, rowMapper, request.getSearch(), request.getSongs().size(),
                    request.getSongs(), request.getId());
            updated += rowMapper.getResult();
        }
        return updated;
    }

    public Collection<Request> getAll() {
        return null;
    }

}
