package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.Genre;
import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.User;

public interface UserService {
    public void create(User user) throws Exception;
    public void createArtist(MusicArtistUser user) throws Exception;
    public User findByUsername(String username);

}
