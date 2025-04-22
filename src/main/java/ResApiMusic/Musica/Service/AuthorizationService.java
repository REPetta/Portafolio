package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.User;

public interface AuthorizationService{
public User authorize(String token) throws Exception;



}
