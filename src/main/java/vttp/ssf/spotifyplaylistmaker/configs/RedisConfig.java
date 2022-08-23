package vttp.ssf.spotifyplaylistmaker.configs;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import vttp.ssf.spotifyplaylistmaker.models.AppUser;

@Configuration
public class RedisConfig {

  private static final Logger logger = LoggerFactory.getLogger(
    RedisConfig.class
  );

  @Value("${spring.redis.host}")
  private String redisHost;

  @Value("${spring.redis.port}")
  private Optional<Integer> redisPort;

  @Value("${spring.redis.password}")
  private String redisPassword;

  @Bean
  @Scope("singleton")
  public RedisTemplate<String, AppUser> redisTemplate() {
    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
    config.setHostName(redisHost);
    config.setPort(redisPort.get());
    config.setPassword(redisPassword);
    Jackson2JsonRedisSerializer jackson2JsonJsonSerializer = new Jackson2JsonRedisSerializer(
      AppUser.class
    );

    final JedisClientConfiguration jedisClient = JedisClientConfiguration
      .builder()
      .build();
    final JedisConnectionFactory jedisFac = new JedisConnectionFactory(
      config,
      jedisClient
    );
    jedisFac.afterPropertiesSet();
    logger.info(
      "redis host port > {redisHost} {redisPort}",
      redisHost,
      redisPort
    );
    RedisTemplate<String, AppUser> template = new RedisTemplate<String, AppUser>();
    template.setConnectionFactory(jedisFac);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(jackson2JsonJsonSerializer);
    template.setHashKeySerializer(template.getKeySerializer());
    template.setHashValueSerializer(template.getValueSerializer());
    return template;
  }
}
