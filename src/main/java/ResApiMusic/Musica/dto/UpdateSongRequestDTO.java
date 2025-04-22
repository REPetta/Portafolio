package ResApiMusic.Musica.dto;

import ResApiMusic.Musica.Model.Genre;


public class UpdateSongRequestDTO {
    private String name;
    private Genre genre;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
