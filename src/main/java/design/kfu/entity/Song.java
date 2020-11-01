package design.kfu.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

public class Song {

    private int id;
    private String url;
    private String salt;
    private String originalUrl;
    private boolean own = false;
    private long time = new Date().getTime() / 1000;

    @JsonIgnoreProperties
    private String songName;

    public Song() {
    }

    public long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public boolean isOwn() {
        return own;
    }

    public void setOwn(boolean own) {
        this.own = own;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Song(String salt, String url, int id) {
        this.salt = salt;
        this.url = url;
        this.id = id;
    }


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Song song = (Song) o;
        return id == song.id;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", original_url='" + originalUrl + '\'' +
                ", songName='" + songName + '\'' +
                '}';
    }
}
