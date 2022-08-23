package vttp.ssf.spotifyplaylistmaker.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vttp.ssf.spotifyplaylistmaker.models.AppPlaylist;
import vttp.ssf.spotifyplaylistmaker.models.AppUser;
import vttp.ssf.spotifyplaylistmaker.repositories.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository userRepo;

  private static final Logger logger = LoggerFactory.getLogger(
    PlaylistMakerService.class
  );

  public AppUser getUser(String username) {
    AppUser user = userRepo.getUserByUsername(username);
    return user;
  }

  public void renamePlaylist(
    String username,
    String newPlaylistName,
    String playlistId
  ) {
    AppUser user = userRepo.getUserByUsername(username);
    AppPlaylist playlist = user
      .getPlaylists()
      .stream()
      .filter(pl -> pl.getId().equals(playlistId))
      .findFirst()
      .orElse(null);
    playlist.setName(newPlaylistName);
    updateUser(user);
  }

  public void updateUser(AppUser user) {
    userRepo.saveUser(user);
  }

  public void addPlaylist(String username, AppPlaylist playlist) {
    // get user
    AppUser user = userRepo.getUserByUsername(username);

    // create new user if does not exist
    if (!userRepo.isExistingUser(username)) {
      user = new AppUser(username);
    }

    // add playlist
    user.getPlaylists().add(playlist);

    // update user
    userRepo.saveUser(user);
    logger.info(
      "Playlist {} has been saved to {}'s playlist collection",
      playlist.getName(),
      username
    );
  }
}
