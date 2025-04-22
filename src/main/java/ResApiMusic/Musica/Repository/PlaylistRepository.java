package ResApiMusic.Musica.Repository;


import ResApiMusic.Musica.Model.Playlist;
import ResApiMusic.Musica.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist,Long> {//extiende de JpaRepository para manejar la base de datos relacionada con Playlist//
    List<Playlist> findByOwner(User owner);


}
