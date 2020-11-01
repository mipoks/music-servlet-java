package design.kfu.service;

import design.kfu.entity.dto.SongForm;
import design.kfu.entity.Person;
import design.kfu.entity.Song;
import design.kfu.helper.database.ConnectionGiver;
import design.kfu.helper.entity.SongOwnChecker;
import design.kfu.music.MusicGetter;
import design.kfu.music.implementation.v1.GetZaycevMusic;
import design.kfu.repository.implementation.SongRepoImpl;
import design.kfu.repository.SongRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class PersonMusicService {

    private ConnectionGiver connectionGiver;
    private MainService mainService;
    private MusicGetter musicGetter;
    private SongOwnChecker songOwnChecker;

    public PersonMusicService() {
        mainService = MainService.getInstance();
        connectionGiver = mainService.getConnectionGiver();
        musicGetter = new GetZaycevMusic();
        songOwnChecker = new SongOwnChecker();
    }

    public Collection<Song> getMusic(HttpServletRequest req, HttpServletResponse resp) {
        Person person = SecurityService.getUser(req);
        SongRepository songRepository = new SongRepoImpl(connectionGiver);
        Collection<Song> songs = songRepository.getByPerson(person);
        songs = songOwnChecker.addParamIfInSongRepo(songs, true);
        try {
            songs = musicGetter.update(songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Song song : songs) {
            songRepository.save(song);
        }
        return songs;
    }

    public int addMusic(SongForm songForm, HttpServletRequest req, HttpServletResponse resp) {
        Person person = SecurityService.getUser(req);
        Song tempSong = new Song();
        tempSong.setId(songForm.getId());
        tempSong.setOriginalUrl(songForm.getUrlOrig());
        tempSong.setSongName(songForm.getName());

        SongRepository songRepository = new SongRepoImpl(person, connectionGiver);

        Collection<Song> collection = songRepository.getByPerson(person);
        if (songOwnChecker.contains(tempSong, collection)) {
            songRepository.delete(tempSong);
            return -1;
        } else {
            songRepository.save(tempSong);
            return 1;
        }
    }
}
