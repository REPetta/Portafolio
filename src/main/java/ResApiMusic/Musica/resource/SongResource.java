package ResApiMusic.Musica.resource;

import ResApiMusic.Musica.Model.Genre;
import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.Song;
import ResApiMusic.Musica.Model.User;
import ResApiMusic.Musica.Service.AuthorizationService;
import ResApiMusic.Musica.Service.SongService;
import ResApiMusic.Musica.dto.SongResponseDTO;
import ResApiMusic.Musica.dto.UpdateSongRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin( origins = "http://127.0.0.1:5501")
@RestController
@RequestMapping("/songs")
public class SongResource {
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private SongService service;

    @CrossOrigin( origins = "http://127.0.0.1:5501")
   // @GetMapping(produces = "application/json")//
    @GetMapping("/getSongs")
    //Metodo validado//
    public ResponseEntity<?> getAllSongs(@RequestHeader(name = "Authorization") String token) {
        try {
            authorizationService.authorize(token);
            List<Song> songs = service.getAll();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.createTypeMap(Song.class, SongResponseDTO.class)
                    .addMapping(src -> src.getAuthor().getArtistname() , (dto, v) -> dto.getArtist().setName((String) v))
                    .addMapping(src -> src.getAuthor().getId(), (dto, v) -> dto.getArtist().setId((Long) v))
                    .addMapping(src -> src.getName(), (dto, v) -> dto.setName((String) v))
                    .addMapping(src -> src.getGenre(), (dto, v) -> dto.setGenre((Genre) v))
                    .addMapping(src -> src.getId(), (dto, v) -> dto.setId((Long) v));

            List<SongResponseDTO> dtos = songs
                    .stream()
                    .map(song -> modelMapper.map(song, SongResponseDTO.class))
                    .collect(Collectors.toList());

            MultiValueMap<String, List> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("songs", dtos);
            return new ResponseEntity<>(multiValueMap, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }
    //Metodo Validado//
    @CrossOrigin( origins = "http://127.0.0.1:5501")
    @GetMapping("/filter")
    public ResponseEntity<?> getSongs(@RequestHeader(name = "Authorization") String token, @RequestParam(required = false) String name, @RequestParam(required = false) Genre genre) {
        try {
            authorizationService.authorize(token);
            // Llamar al servicio para obtener las canciones filtradas//
            List<Song> songs = List.of();
            if (name == null && genre == null) {
                songs = service.getAll();
            } else if (genre == null) {
                songs = service.getByArtist(name);
            } else if (name == null) {
                songs = service.getByGenre(genre);
            } else {
                songs = service.getByGenreAndArtist(genre, name);
            }
            // Mapear las canciones a una lista de DTOs

            ModelMapper modelMapper = new ModelMapper();
            modelMapper.createTypeMap(Song.class, SongResponseDTO.class)
                    .addMapping(src -> src.getAuthor().getArtistname(), (dto, v) -> dto.getArtist().setName((String) v))
                    .addMapping(src -> src.getAuthor().getId(), (dto, v) -> dto.getArtist().setId((Long) v))
                    .addMapping(src -> src.getName(), (dto, v) -> dto.setName((String) v))
                    .addMapping(src -> src.getGenre(), (dto, v) -> dto.setGenre((Genre) v))
                    .addMapping(src -> src.getId(), (dto, v) -> dto.setId((Long) v));

            // Configurar el mapeo de atributos compuestos
            List<SongResponseDTO> songResponseDTOS = songs.stream()
                    .map(song -> modelMapper.map(song, SongResponseDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(songResponseDTOS);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<?> getSong(@RequestHeader(name = "Authorization") String token, @PathVariable Long id) {
        try {
            authorizationService.authorize(token);
            //llamar al servicio para obtener la cancion especifica//
            Song songDB = service.GetSongId(id);
            //mapear la cancion a un dtos//
            ModelMapper modelMapper = new ModelMapper();
            SongResponseDTO songResponseDTO = modelMapper.map(songDB, SongResponseDTO.class);
            //retorno la cancion especifica//
            return ResponseEntity.ok(songResponseDTO);
        } catch (Exception e) { //en caso de que no se encuentre la cancion//
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }
    @CrossOrigin( origins = "http://127.0.0.1:5501")
    //metodo validado//
    @PostMapping("/user/createSong")
    public ResponseEntity<?> createSong(@RequestHeader(name = "Authorization") String token, @RequestBody SongResponseDTO songResponseDTO) {

        try {
            MusicArtistUser artistUser = (MusicArtistUser) authorizationService.authorize(token);
            //vamos a mapear el dtp a una entidad song
            ModelMapper modelMapper = new ModelMapper();
            Song song = modelMapper.map(songResponseDTO, Song.class);
            song.setAuthor((MusicArtistUser) artistUser);
            Song createSong = service.createSong(song);
            // Mapear la canción creada a un DTO de respuesta//
            /*SongResponseDTO songResponseDTO1 = modelMapper.map(createSong, SongResponseDTO.class);
            songResponseDTO1.setArtist(new SongResponseDTO.Artist());
            songResponseDTO1.getArtist().setName(artistUser.getUsername());
            songResponseDTO1.getArtist().setId((Long) artistUser.getId());
            songResponseDTO1.setName(songResponseDTO.getName());
            songResponseDTO1.setGenre(songResponseDTO.getGenre());
            //returno la cancion creada//
            return ResponseEntity.status(HttpStatus.CREATED).body(songResponseDTO1);*/
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }

        return null;
    }
    @CrossOrigin( origins = "http://127.0.0.1:5501")
    @PutMapping("/update/{id}")
    //Metodo validado//
    public ResponseEntity<?> updateSong(@RequestHeader(name = "Authorization") String token, @PathVariable Long id, @RequestBody UpdateSongRequestDTO updateSongRequestDTO) {
        try {
            User user = authorizationService.authorize(token); //obtenemos el usuario mediante el token//
            service.updateSong(id, updateSongRequestDTO.getName(), updateSongRequestDTO.getGenre(), user); //llamo al servicio y le paso los parametros
            return ResponseEntity.status(200).build();

        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @CrossOrigin( origins = "http://127.0.0.1:5501")
    @DeleteMapping("/delete/{id}") //Metodo Validado//
    public ResponseEntity<?> deleteSong(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            User user = authorizationService.authorize(token);
            Song song = service.GetSongId(id);
            service.deleteSong(id, user);
            return ResponseEntity.status(204).build();

        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);

        }

    }

    //Metodo Validado//
    @CrossOrigin( "http://127.0.0.1:5501")
    @GetMapping("/songs/getUserSongs")
    public ResponseEntity<?> getUserSongs(@RequestHeader("Authorization") String token) {
        try {
            User user = authorizationService.authorize(token);

            List<Song>songs=service.getUserSong(user);
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.createTypeMap(Song.class, SongResponseDTO.class)
                    .addMapping(src -> src.getAuthor().getArtistname(), (dto, v) -> dto.getArtist().setName((String) v))
                    .addMapping(src -> src.getAuthor().getId(), (dto, v) -> dto.getArtist().setId((Long) v))
                    .addMapping(src -> src.getName(), (dto, v) -> dto.setName((String) v))
                    .addMapping(src -> src.getGenre(), (dto, v) -> dto.setGenre((Genre) v))
                    .addMapping(src -> src.getId(), (dto, v) -> dto.setId((Long) v));
            List<SongResponseDTO>songResponseDTOS= songs.stream()
                    .map(song -> modelMapper.map(song,SongResponseDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(songResponseDTOS);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }



}