package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.Genre;
import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.Song;
import ResApiMusic.Musica.Model.User;
import ResApiMusic.Musica.Repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service


public class SongServiceImp implements SongService{
    @Autowired
    private SongRepository repository;

    @Override
    public List<Song> getAll() {
        return repository.findAll();
    }
    @Override
    public  List<Song> getByGenre(Genre genre) {
        return repository.findByGenre(genre);
    }
    @Override
    public List<Song> getByArtist(String artistUsername) {
        return repository.findByAuthorArtistname(artistUsername);
    }

    @Override
    public List<Song> getByGenreAndArtist(Genre genre, String artistUsername) {
        return repository.findByGenreAndAuthorArtistname(genre,artistUsername);
    }

    @Override
    public Song GetSongId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cancion no encontrada"));


}

    @Override
    public Song createSong(Song song) {
        return repository.save(song);
    }

    @Override
    public void updateSong(Long id, String name, Genre genre, User user) throws Exception {
        // Busca la canción en el repositorio por su ID//
        Song song= repository.findById(id).orElse(null);
        if (song == null){
            throw new Exception("cancion no encontrada");// si la cancion no existe retorna un mensaje//
        }
        if (!song.getAuthor().equals(user)){
            throw new Exception("No estas autorizado a actualizar esta cancion"); //si el usuario no tiene el permiso retorna un mensaje//
        }

        //actualizo la cancion//Metodo validado//
        song.setName(name);
        song.setGenre(genre);
        //guardo la cancion//
        repository.save(song);
    }

    @Override
    public void deleteSong(Long id, User user) throws Exception {
        Song song= repository.findById(id).orElse(null);
        if(song == null){
            throw  new Exception("la cancion no fue encontrada");
        }
        if (!song.getAuthor().equals(user)){
            throw new Exception("no estas autorizado a borrar la cancion");
        }
        repository.delete(song);
    }

    @Override
    public List<Song> getUserSong(User user) {
        return repository.findByAuthor(user);
    }

}
