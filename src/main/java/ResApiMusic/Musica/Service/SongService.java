package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.Genre;
import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.Song;
import ResApiMusic.Musica.Model.User;

import java.util.List;
import java.util.Optional;

public interface SongService {
    public List<Song> getAll();
    public List<Song> getByGenre(Genre genre);
    public List<Song> getByArtist(String artistUsername);
    public List<Song> getByGenreAndArtist(Genre genre, String artistName);
    public Song GetSongId(Long id);
    public Song createSong(Song song);
    public void  updateSong(Long id, String name, Genre genre, User user)throws Exception;
    public void deleteSong(Long id,User user) throws Exception;
    public List<Song>getUserSong(User user);


}
