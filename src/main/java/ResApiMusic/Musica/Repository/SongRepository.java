package ResApiMusic.Musica.Repository;

import ResApiMusic.Musica.Model.Genre;
import ResApiMusic.Musica.Model.Song;
import ResApiMusic.Musica.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongRepository extends JpaRepository<Song,Long> {
    List<Song> findByAuthorArtistname(String username);
    List<Song> findByGenreAndAuthorArtistname(Genre genre, String artistName);
    List<Song> findByGenre(Genre genre);
    List<Song> findByAuthor(User author);

}
