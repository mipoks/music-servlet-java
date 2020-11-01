package design.kfu.helper.entity;

import design.kfu.entity.Song;

import java.util.Collection;

public class SongOwnChecker {


    public Collection<Song> addParamIfInSongRepo(Collection<Song> toFind, Collection<Song> from) {
        for (Song song : toFind) {
            song.setOwn(contains(song, from));
        }
        return toFind;
    }

    public Collection<Song> addParamIfInSongRepo(Collection<Song> collection, boolean own) {
        for (Song song : collection) {
            song.setOwn(own);
        }
        return collection;
    }

    public boolean contains(Song song, Collection<Song> from) {
        for (Song songIn : from) {
            if (songIn.equals(song))
                return true;
        }
        return false;
    }
}
