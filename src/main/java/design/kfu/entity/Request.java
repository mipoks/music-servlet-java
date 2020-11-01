package design.kfu.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;

public class Request {

    @NotNull
    @Size(min=2)
    private String search;
    private Long time;
    private int id;
    private Collection<Song> songs;

    public Request(@NotNull @Size(min = 2) String search) {
        this.search = search;
        this.time = new Date().getTime() / 1000;
    }

    public Request() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
