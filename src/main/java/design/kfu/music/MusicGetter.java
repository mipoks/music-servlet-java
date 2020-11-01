package design.kfu.music;

import design.kfu.entity.Request;
import design.kfu.entity.Song;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

public interface MusicGetter {
    Collection<Song> get(int count);
    Collection<Song> get(int count, Request request);
    Collection<Song> update(Collection<Song> collection) throws IOException;
}
