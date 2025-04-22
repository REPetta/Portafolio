package ResApiMusic.Musica.Service;

import ResApiMusic.Musica.Model.User;
import ResApiMusic.Musica.Util.JwtTokenUtil;
import ResApiMusic.Musica.Util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticathionService{
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public String authenticate(User user) throws Exception {
        User userDB = userService.findByUsername(user.getUsername());
        if(userDB==null) throw new Exception();
        if(!passwordEncoder.verify(
                user.getPassword(),
                userDB.getPassword())) throw new Exception();
        return jwtTokenUtil.generateToken(user.getUsername());
    }
}

