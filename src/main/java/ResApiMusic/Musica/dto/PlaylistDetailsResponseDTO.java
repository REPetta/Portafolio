package ResApiMusic.Musica.dto;

import java.util.List;

public class PlaylistDetailsResponseDTO {
    private String name;
    private  int songCount;
    private List<SongResponseDTO>songs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public List<SongResponseDTO> getSongs() {
        return songs;
    }

    public void setSongs(List<SongResponseDTO> songs) {
        this.songs = songs;
    }
}
