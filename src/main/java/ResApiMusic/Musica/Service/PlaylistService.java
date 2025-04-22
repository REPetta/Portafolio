package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.Playlist;
import ResApiMusic.Musica.Model.Song;
import ResApiMusic.Musica.Model.User;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    public List<Playlist> getallPlaylist();
    Optional<Playlist> getPlaylistById(Long id);//el optional es una clase contenedora //
    public  Playlist createPlaylist(String name,User user);
    Playlist  updatePlaylist(Long id,String name)throws Exception;
    public void DeletePlaylist(Long id, User user)throws Exception;
    public Playlist addSongPlaylist(Long playlistId,Long songId,User user) throws Exception;
    public  Playlist removeSongFomPlaylist(Long playlistId, Long songId,User user)throws Exception;
    public List<Playlist>getPlaylistByUser(User user) throws Exception;

}
