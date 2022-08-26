package vttp.ssf.spotifyplaylistmaker.configs;

import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Configuration
public class SpotifyApiConfig {

  @Value("${spotify.client-id}")
  private String clientId;

  @Value("${spotify.client-secret}")
  private String clientSecret;

  private static final Logger logger = LoggerFactory.getLogger(
    SpotifyApiConfig.class
  );

  // NOTE: set to request for new token after prev token expires every 60 min
  @Bean
  @Scheduled(fixedDelay = 3600000)
  public SpotifyApi initSpotifyApi() {
    SpotifyApi spotifyApi = new SpotifyApi.Builder()
      .setClientId(clientId)
      .setClientSecret(clientSecret)
      .build();

    ClientCredentialsRequest clientCredentialsRequest = spotifyApi
      .clientCredentials()
      .build();

    try {
      final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
      spotifyApi.setAccessToken(clientCredentials.getAccessToken());
      logger.info("Request for Spotify access token successful");
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      logger.error(e.getMessage());
    }

    return spotifyApi;
  }
}
