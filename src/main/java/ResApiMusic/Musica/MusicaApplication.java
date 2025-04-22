package ResApiMusic.Musica;

import ResApiMusic.Musica.Util.JwtTokenUtil;
import ResApiMusic.Musica.Util.PasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MusicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicaApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new PasswordEncoder();

	}
	@Bean
	public JwtTokenUtil jwtTokenUtil() {
		return new JwtTokenUtil();
	}
}
