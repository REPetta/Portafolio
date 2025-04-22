package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.Playlist;
import ResApiMusic.Musica.Model.Song;
import ResApiMusic.Musica.Model.User;
import ResApiMusic.Musica.Repository.PlaylistRepository;
import ResApiMusic.Musica.Repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class PlaylistServiceImp implements PlaylistService{
    @Autowired
    private PlaylistRepository repository;

    @Autowired
    private SongRepository songRepository;


    @Override
    public List<Playlist> getallPlaylist() { //este metodo recupera todas las playlist de el repositorio//
        return repository.findAll();
    }

    @Override
    public Optional<Playlist> getPlaylistById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Playlist createPlaylist(String name, User user) {
        Playlist playlist=new Playlist();
        playlist.setName(name);
        playlist.setOwner(user);
        playlist.setSongs(new ArrayList<>());
        return repository.save(playlist);
    }


    //metodo para actualizar el nombre de la playlist//
    @Override
    public  Playlist updatePlaylist(Long id, String name) throws Exception {
        //necesito buscar la playlist para hacerle el update//
        Playlist playlist=repository.findById(id).orElseThrow();
        if (playlist == null) {//si no encuentro la playlist//
             throw new Exception("Playlist no encontrada");
        }
        //seteo el nuevo nombre//
       playlist.setName(name);
        //guardo los cambios//
        return repository.save(playlist);
    }

    @Override
    public void DeletePlaylist(Long id, User user)throws Exception  {
        Playlist playlist=repository.findById(id).orElseThrow();
        if (playlist== null){ //si no existe la playlist//
            throw new Exception("Playlist no encontrada");
        }
        if (!playlist.getOwner().equals(user)){ //no se si es correcto verificar asi que sea el dueño de la playlist//
            throw new Exception("No estas autorizado a eliminar esta Playlist");
        }
        repository.delete(playlist);// elimino la palylist//

    }
    //metodo que se utilizara para agragar una cancion a una playlist//
    @Override
    public Playlist addSongPlaylist(Long playlistid,Long songId,User user) throws Exception {
        Playlist playlist= repository.findById(playlistid).orElseThrow();
        if(playlist== null){ //si la playlist no se encuentra//
            throw new Exception("Playlist no encontrada");

        }
        if (!playlist.getOwner().equals(user)){ //no se si es correcto verificar asi que sea el dueño de la playlist//
            throw new Exception("No estas autorizado para agregar una cancion a esta playlist");

    }
        Song song=songRepository.findById(songId).orElse(null);
        if (song == null){ //verifico que la cancion que quiero agregar exista//
        throw new Exception("La cancion no exite");
        }
        playlist.getSongs().add(song); //agrego la cancion//
        return repository.save(playlist); //guardo la playlist//
    }

    //metodo encargado de eliminar una cancion de la playlist//
    //metodo validado//
    @Override
    public Playlist removeSongFomPlaylist(Long playlistId, Long songId, User user) throws Exception {
        Playlist playlist= repository.findById(playlistId).orElseThrow();
        if(playlist== null){ //si la playlist no se encuentra//songId
            throw new Exception("Playlist no encontrada");
    }
        if (!playlist.getOwner().equals(user)){ //no se si es correcto verificar asi que sea el dueño de la playlist//
            throw new Exception("No estas autorizado a eliminar esta Playlist");
}
        Song song=songRepository.findById(songId).orElse(null);
        if (song == null) { //verifico que la cancion que quiero agregar exista//
            throw new Exception("La cancion no exite");
        }
        if(!playlist.getSongs().remove(song)){
            throw new Exception("La canción no está en la playlist");
        }
        return repository.save(playlist);
    }

    @Override
    public List<Playlist> getPlaylistByUser(User owner ) throws Exception {
        return repository.findByOwner(owner);

    }
}