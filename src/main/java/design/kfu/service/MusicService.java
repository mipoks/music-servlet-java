package design.kfu.service;

import design.kfu.entity.Person;
import design.kfu.entity.Request;
import design.kfu.entity.Song;
import design.kfu.helper.database.ConnectionGiver;
import design.kfu.helper.entity.SongOwnChecker;
import design.kfu.music.MusicGetter;
import design.kfu.music.implementation.v1.GetZaycevMusic;
import design.kfu.repository.implementation.RequestRepoImpl;
import design.kfu.repository.RequestRepository;
import design.kfu.repository.implementation.SongRepoImpl;
import design.kfu.repository.SongRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class MusicService {

    private MainService mainService;
    private RequestRepository requestRepo;
    private SongRepository songRepository;
    private SongOwnChecker songOwnChecker;
    private ConnectionGiver connectionGiver;
    private MusicGetter musicGetter;

    public MusicService() {
        mainService = MainService.getInstance();
        connectionGiver = mainService.getConnectionGiver();
        requestRepo = new RequestRepoImpl(connectionGiver);
        songRepository = new SongRepoImpl(connectionGiver);
        songOwnChecker = new SongOwnChecker();
        musicGetter = new GetZaycevMusic();
    }

    public String getRandomMusicName() throws SQLException {
        String name = null;
        String sql = "SELECT name FROM songs ORDER BY random() LIMIT 1";
        Connection connection = connectionGiver.getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet != null && resultSet.next()) {
            name = resultSet.getString("name");
        }
        try {
            connection.close();
        } catch (Exception ex) {
        }
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    public Collection<Song> search(String search, HttpServletRequest req) throws IOException {
        int count = 5;

        Request request = requestRepo.findBySearch(search);
        Collection<Song> songs;
        Person person = SecurityService.getUser(req);

        if (person != null) {
            count = person.getSongCount();
        }

        if (request != null && request.getSongs().size() >= count) {
            if (request.getTime() > new Date().getTime() / 1000 - MainService.TIMEOUT_REQUEST) {
                Collection<Song> songs2 = songRepository.getByRequest(request);
                songs = new ArrayList<>();
                Iterator it = songs2.iterator();
                for (int i = 0; i < count; i++) {
                    songs.add((Song) it.next());
                }
                songs = musicGetter.update(songs);
            } else {
                if (search.equals(""))
                    songs = musicGetter.get(count);
                else
                    songs = musicGetter.get(count, request);
                songs = musicGetter.update(songs);
                request.setSongs(songs);
                request.setTime(new Date().getTime() / 1000);
                requestRepo.save(request);
            }
        } else {
            request = new Request(search);
            request.setTime(new Date().getTime() / 1000);
            if (search.equals(""))
                songs = musicGetter.get(count);
            else
                songs = musicGetter.get(count, request);
            songs = musicGetter.update(songs);

            request.setSongs(songs);
            requestRepo.save(request);
        }
        for (Song song : songs)
            songRepository.save(song);

        if (person != null) {
            songs = songOwnChecker.addParamIfInSongRepo(songs, songRepository.getByPerson(person));
        }
        return songs;
    }


    public Collection<Song> getPopularSongs(HttpServletRequest req) throws IOException {
        return search("", req);
    }

}
