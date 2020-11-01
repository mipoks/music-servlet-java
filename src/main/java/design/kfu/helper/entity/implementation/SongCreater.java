package design.kfu.helper.entity.implementation;

import design.kfu.entity.Song;
import design.kfu.helper.entity.EntityCreater;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongCreater implements EntityCreater {
    public Song create(ResultSet resultSet) throws SQLException {
        Song tempSong = new Song();
        tempSong.setSongName(resultSet.getString("name"));
        tempSong.setUrl(resultSet.getString("url"));
        tempSong.setOriginalUrl(resultSet.getString("original_url"));
        tempSong.setId(resultSet.getInt("id"));
        tempSong.setTime(resultSet.getLong("updated"));
        return  tempSong;
    }
}
