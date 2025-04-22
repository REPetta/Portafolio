package ResApiMusic.Musica.resource;

import ResApiMusic.Musica.Model.MusicEnthusiastUser;
import ResApiMusic.Musica.Service.AuthenticathionService;
import ResApiMusic.Musica.Service.UserService;
import ResApiMusic.Musica.dto.AuthenticationRequestDTO;
import ResApiMusic.Musica.dto.CreateUserRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Enthusiast")
public class MusicEnthusiatUserResource {
    @Autowired
    private UserService service;
    @Autowired
    private AuthenticathionService authenticathionService;

    @CrossOrigin( origins = "http://127.0.0.1:5501")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserRequestDTO userDTO){
        ModelMapper modelMapper=new ModelMapper();
        MusicEnthusiastUser user=modelMapper.map(userDTO,MusicEnthusiastUser.class);
        try{
            service.create(user);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }

    }

    @PostMapping(path="/auth",produces = "application/json")
    public ResponseEntity<?>authentication (@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
       ModelMapper modelMapper=new ModelMapper();
       MusicEnthusiastUser musicEnthusiast = modelMapper.map(authenticationRequestDTO,MusicEnthusiastUser.class);
       try{
           String token=authenticathionService.authenticate(musicEnthusiast);
           MultiValueMap<String,String> multiValueMap= new LinkedMultiValueMap<>();
           multiValueMap.add("token",token);
           return new ResponseEntity<>(token,null,HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
       }
    }

}
