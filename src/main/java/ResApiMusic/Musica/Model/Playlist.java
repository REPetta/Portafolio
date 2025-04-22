package ResApiMusic.Musica.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Playlist")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "idUsuario",referencedColumnName = "id",nullable = false)
    private User owner;
    @ManyToMany
    @JoinTable(
            name = "playlist_song", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "idPlayList"), // FK de PlayList
            inverseJoinColumns = @JoinColumn(name = "idSong") // FK de Song
    )


    private List<Song> songs = new ArrayList<>();

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


}
