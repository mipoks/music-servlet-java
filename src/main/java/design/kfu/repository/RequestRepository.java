package design.kfu.repository;

import design.kfu.entity.Request;

import java.util.Collection;

public interface RequestRepository extends States {
    int save(Request request);
    Collection<Request> getAll();
    Request findBySearch(String search);
    int delete(Request request);
    int update(Request request);
}
