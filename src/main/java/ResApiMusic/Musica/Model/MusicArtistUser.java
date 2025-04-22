package ResApiMusic.Musica.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ARTIST") // Valor que se guarda en la columna user_type

public class MusicArtistUser extends User {
    private String artistname;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Song> songs =new ArrayList<Song>();

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String name) {
        this.artistname = name;
    }

    @Override
    public boolean createSong() {
        return true;
    }
}
