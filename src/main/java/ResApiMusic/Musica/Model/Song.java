package ResApiMusic.Musica.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "song")

public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "author",referencedColumnName = "id",nullable = false)
    private MusicArtistUser author;
    @ManyToMany(mappedBy = "songs") // No se define la tabla intermedia aquí, ya está en PlayList
    private List<Playlist> playlists = new ArrayList<>();
    private Genre genre;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MusicArtistUser getAuthor() {
        return author;
    }

    public void setAuthor(MusicArtistUser author) {
        this.author = author;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
