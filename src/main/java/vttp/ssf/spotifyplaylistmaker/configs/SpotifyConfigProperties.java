package vttp.ssf.spotifyplaylistmaker.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spotify")
public record SpotifyConfigProperties(String clientId, String clientSecret) {

}