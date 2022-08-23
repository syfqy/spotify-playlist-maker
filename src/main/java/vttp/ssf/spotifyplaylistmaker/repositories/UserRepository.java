package vttp.ssf.spotifyplaylistmaker.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp.ssf.spotifyplaylistmaker.models.AppUser;

@Repository
public class UserRepository {

  @Autowired
  RedisTemplate<String, AppUser> redisTemplate;

  private static final Logger logger = LoggerFactory.getLogger(
    UserRepository.class
  );

  public boolean isExistingUser(final String username) {
    return redisTemplate.hasKey(username);
  }

  public AppUser getUserByUsername(final String username) {
    AppUser user = (AppUser) redisTemplate.opsForValue().get(username);
    logger.info("Returning user {} from database", username);
    return user;
  }

  public void saveUser(final AppUser user) {
    redisTemplate.opsForValue().set(user.getUsername(), user);
    logger.info("Updated user {} in database", user.getUsername());
  }
}
