package design.kfu.music.implementation.v1;


import design.kfu.entity.Request;
import design.kfu.entity.Song;
import design.kfu.music.MusicGetter;
import design.kfu.service.MainService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;


public class GetZaycevMusic implements MusicGetter {
    private static String urlMusic = "https://zaycev.net";

    private Collection<Song> getDoc(String uri, int count) {
        System.out.println("WE ARE CONNECTING TO ZAYCEV");
        try {
            Document doc = Jsoup.connect(urlMusic + "/" + uri).get();
            Elements elements = doc.getElementsByAttribute("data-url");
            List<Song> songs = new ArrayList<>();
            int num = 0;
            for (Element elem : elements) {
                String dataurl = elem.attributes().get("data-url");
                System.out.println(dataurl);
                System.out.println(urlMusic + "/" + uri);
                if (dataurl.length() > 5 && dataurl.substring(dataurl.length() - 5).contains("json")) {

                    ObjectMapper mapper = new ObjectMapper(); // just need one
                    // Got a Java class that data maps to nicely? If so:
                    Song songTemp = mapper.readValue(new URL(urlMusic + dataurl), Song.class);
                    // Or: if no class (and don't need one), just map to Map.class:

                    songTemp.setSongName(elem.text().substring(0, elem.text().length() - 6));
                    songTemp.setOriginalUrl(dataurl);
                    System.out.println(songTemp);
                    songs.add(songTemp);
                    num++;
                }
                if (num == count)
                    break;
            }
            return update(songs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Song> update(Collection<Song> list) throws IOException {
        Iterator it = list.iterator();
        Collection<Song> songs = new ArrayList<>();
        while (it.hasNext()) {
            Song oldSong = (Song) it.next();
            if (oldSong.getTime() < new Date().getTime() / 1000 - MainService.TIMEOUT_SONG) {
                ObjectMapper mapper = new ObjectMapper(); // just need one
                // Got a Java class that data maps to nicely? If so:
                Song songTemp = mapper.readValue(new URL(urlMusic + oldSong.getOriginalUrl()), Song.class);
                // Or: if no class (and don't need one), just map to Map.class:
                System.out.println(oldSong.getOriginalUrl() + "     " + songTemp.getUrl());
                songTemp.setOriginalUrl(oldSong.getOriginalUrl());
                songTemp.setSongName(oldSong.getSongName());
                songTemp.setTime(new Date().getTime() / 1000);
                songTemp.setOwn(oldSong.isOwn());
                oldSong = songTemp;
            }
            songs.add(oldSong);
        }
        return songs;

    }

    public Collection<Song> get(int count, Request request) {
        return getDoc("search.html?query_search=" + request.getSearch(), count);
    }

    public Collection<Song> get(int count) {
        return getDoc("", count);
    }


}
