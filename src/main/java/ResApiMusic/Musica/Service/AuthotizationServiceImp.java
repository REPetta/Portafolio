package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.MusicArtistUser;
import ResApiMusic.Musica.Model.User;
import ResApiMusic.Musica.Util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthotizationServiceImp implements  AuthorizationService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Override
    public User authorize(String token) throws Exception {
        if (!jwtTokenUtil.verify(token)) throw new Exception();
        String username = jwtTokenUtil.getSubject(token);
        User user = userService.findByUsername(username);
        if (user == null) throw new Exception();
        return user;
    }


}