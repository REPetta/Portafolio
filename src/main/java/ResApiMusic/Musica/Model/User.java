package ResApiMusic.Musica.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table( name = "Usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Usa una sola tabla para User y sus hijos
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING) // Define el tipo de usuario

public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> playlists =new ArrayList<>();

    public List<Playlist> getPlatlists() {
        return playlists;
    }

    public void setPlatlists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public abstract boolean createSong();
}
