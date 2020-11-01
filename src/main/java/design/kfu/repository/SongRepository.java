package design.kfu.repository;

import design.kfu.entity.Person;
import design.kfu.entity.Request;
import design.kfu.entity.Song;

import java.util.Collection;

public interface SongRepository extends States{
    int save(Song song);
    Collection<Song> getAll();
    int delete(Song song);
    int update(Song song);
    Collection<Song> getByRequest(Request request);
    Collection<Song> getByPerson(Person person);
}
