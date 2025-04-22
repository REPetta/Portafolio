package ResApiMusic.Musica.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ENTHUSIAST") // Valor que se guarda en la columna user_type

public class MusicEnthusiastUser extends User {
    @Override
    public boolean createSong() {
        return false;
    }
}
