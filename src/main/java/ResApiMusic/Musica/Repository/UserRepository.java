package ResApiMusic.Musica.Repository;

import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>{
    public User findByUsername(String username);

}
