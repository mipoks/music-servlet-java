package design.kfu.repository.implementation;

import design.kfu.entity.Request;
import design.kfu.helper.database.ConnectionGiver;
import design.kfu.helper.database.JdbcTemplate;
import design.kfu.repository.RequestRepository;
import design.kfu.repository.implementation.database.SQLRequestSaver;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

public class RequestRepoImpl implements RequestRepository {

    private SQLRequestSaver sqlRequestSaver;
    private JdbcTemplate jdbcTemplate;

    public RequestRepoImpl(ConnectionGiver connectionGiver) {
        jdbcTemplate = new JdbcTemplate(connectionGiver);
        sqlRequestSaver = new SQLRequestSaver(jdbcTemplate);
    }

    @Override
    public int save(Request request) {
        Collection<Request> collection = new ArrayList<>();
        collection.add(request);
        return sqlRequestSaver.insert(collection);
    }

    @Override
    public Collection<Request> getAll() {
        return sqlRequestSaver.getAll();
    }

    @Override
    public Request findBySearch(String search) {
        return sqlRequestSaver.get(search);
    }

    @Override
    public int delete(Request request) {
        Collection<Request> collection = new ArrayList<>();
        collection.add(request);
        return sqlRequestSaver.delete(collection);
    }

    @Override
    public int update(Request request) {
        Collection<Request> collection = new ArrayList<>();
        collection.add(request);
        return sqlRequestSaver.update(collection);
    }

}
