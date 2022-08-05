package vttp.ssf.spotifyplaylistmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import vttp.ssf.spotifyplaylistmaker.configs.SpotifyConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(SpotifyConfigProperties.class)
public class SpotifyPlaylistMakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyPlaylistMakerApplication.class, args);
	}

}
