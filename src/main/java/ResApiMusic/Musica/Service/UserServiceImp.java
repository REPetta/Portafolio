package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.User;
import ResApiMusic.Musica.Repository.UserRepository;
import ResApiMusic.Musica.Util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void create(User user) throws Exception { //metodo para crear un nuevo usuario//
        User userDB = repository.findByUsername(user.getUsername());
        if (userDB != null){
            throw  new Exception();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
    @Override
    public void createArtist(MusicArtistUser user) throws Exception { //metodo para crear un nuevo usuario//
        User userDB = repository.findByUsername(user.getUsername());
        if (userDB != null){
            throw  new Exception();
        }
        if(user.getArtistname()==null){
            throw  new Exception();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setArtistname(user.getArtistname());
        repository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
