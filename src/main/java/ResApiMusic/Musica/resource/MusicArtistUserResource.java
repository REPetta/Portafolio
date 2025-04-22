package ResApiMusic.Musica.resource;

import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.User;
import ResApiMusic.Musica.Service.AuthenticathionService;
import ResApiMusic.Musica.Service.UserService;
import ResApiMusic.Musica.dto.AuthenticationRequestDTO;
import ResApiMusic.Musica.dto.CreateUserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import ResApiMusic.Musica.Util.JwtTokenUtil;

@CrossOrigin( origins = "http://127.0.0.1:5501")//
@RestController
@RequestMapping("/Artist")

public class MusicArtistUserResource {
    @Autowired
    private UserService service;
    @Autowired
    private AuthenticathionService authenticathionService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @GetMapping("/me")
    public ResponseEntity<?> getArtistInfo(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>("Token inválido o ausente", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.replace("Bearer ", "");
            String username = jwtTokenUtil.getSubject(token);

            // Buscar el usuario (asumiendo que devuelve un MusicArtistUser)
            MusicArtistUser artist = (MusicArtistUser) service.findByUsername(username);
            if (artist == null) {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }

            // Mapear a CreateUserRequestDTO
            CreateUserRequestDTO dto = new CreateUserRequestDTO();
            dto.setUsername(artist.getUsername());
            dto.setArtistname(artist.getArtistname());
            // ⚠️ Opcional: no deberías mandar el password, pero si lo querés dejar vacío:
            dto.setPassword(""); // o null

            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return new ResponseEntity<>("Error al obtener los datos del artista", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin( origins = "http://127.0.0.1:5501")//
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserRequestDTO userDTO){
        ModelMapper modelMapper = new ModelMapper();
        MusicArtistUser user =modelMapper.map(userDTO,MusicArtistUser.class);
        try{
            service.createArtist(user);
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }

    }
    @CrossOrigin( origins = "http://127.0.0.1:5501")//

    @PostMapping(path="/auth",produces = "application/json")
    public ResponseEntity<?> authentication (@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
        ModelMapper modelMapper=new ModelMapper();
        MusicArtistUser artistUser =modelMapper.map(authenticationRequestDTO,MusicArtistUser.class);
        try{
            String token = authenticathionService.authenticate(artistUser);
            MultiValueMap<String,String>multiValueMap=new LinkedMultiValueMap<>();
            multiValueMap.add("token", token);
            return new ResponseEntity<>(token,null,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }




    }






}
