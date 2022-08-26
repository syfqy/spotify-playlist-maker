package vttp.ssf.spotifyplaylistmaker.configs;

import java.io.IOException;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

@Configuration
@EnableScheduling
public class SpotifyApiConfig {

  @Value("${spotify.client-id}")
  private String clientId;

  @Value("${spotify.client-secret}")
  private String clientSecret;

  private static final Logger logger = LoggerFactory.getLogger(
    SpotifyApiConfig.class
  );

  // NOTE: schedule request new access token every 55 min (expires every 60 min)
  private static final long accessTokenExpiryMs = 3300000;

  public static SpotifyApi spotifyApi;
  private static ClientCredentialsRequest clientCredentialsRequest;
  private static ClientCredentials clientCredentials;

  @Scheduled(fixedDelay = accessTokenExpiryMs)
  private void getNewAccessToken()
    throws ParseException, SpotifyWebApiException, IOException {
    clientCredentials = clientCredentialsRequest.execute();
    spotifyApi.setAccessToken(clientCredentials.getAccessToken());
    logger.info("Request for new Spotify API access token successful");
  }

  @Bean
  public SpotifyApi initSpotifyApi() {
    spotifyApi =
      new SpotifyApi.Builder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .build();

    clientCredentialsRequest = spotifyApi.clientCredentials().build();

    try {
      getNewAccessToken();
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      logger.error(e.getMessage());
    }

    return spotifyApi;
  }
}
