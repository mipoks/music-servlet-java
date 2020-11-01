package design.kfu.helper.entity.implementation;

import design.kfu.entity.Request;
import design.kfu.entity.Song;
import design.kfu.helper.entity.EntityCreater;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class RequestCreater implements EntityCreater {
    public Request create(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setSearch(resultSet.getString("req_name"));
        request.setTime(resultSet.getLong("time"));
        request.setId(resultSet.getInt("id"));
//        int len = resultSet.getInt("length");
//        ObjectMapper mapper = new ObjectMapper();
        Collection<Song> songs = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(resultSet.getString("songs"));

        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()) {
            int id = Integer.parseInt(keys.next());
            Song song = new Song();
            song.setId(id);
            System.out.println(id);
            songs.add(song);
        }
        request.setSongs(songs);
        return  request;
    }
}
