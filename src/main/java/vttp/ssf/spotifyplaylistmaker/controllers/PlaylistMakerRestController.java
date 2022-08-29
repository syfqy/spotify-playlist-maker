package vttp.ssf.spotifyplaylistmaker.controllers;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vttp.ssf.spotifyplaylistmaker.models.AppPlaylist;
import vttp.ssf.spotifyplaylistmaker.models.AppUser;
import vttp.ssf.spotifyplaylistmaker.services.PlaylistMakerService;
import vttp.ssf.spotifyplaylistmaker.services.UserService;

@RestController
@RequestMapping("/api")
public class PlaylistMakerRestController {

  @Autowired
  PlaylistMakerService plmService;

  @Autowired
  UserService userService;

  private static final Logger logger = LoggerFactory.getLogger(
    PlaylistMakerRestController.class
  );

  @GetMapping("/search")
  public ResponseEntity<AppPlaylist> searchPlaylists(
    @RequestParam(name = "keyword") String keyword,
    @RequestParam(name = "tracks") int nTracks,
    @RequestParam("playlists") int nPlaylists
  ) {
    // NOTE: can only request max of 50 playlist from Spotify API
    // TODO: can support >50 via requesting in batches and using offset param;
    nPlaylists = nPlaylists > 50 ? 50 : nPlaylists;

    logger.info(
      "Generating playlist of {} tracks via mining {} playlists about: {}",
      nTracks,
      nPlaylists,
      keyword
    );
    AppPlaylist topTracksList = plmService.getTopTracksOfKeyword(
      nTracks,
      nPlaylists,
      keyword
    );
    return ResponseEntity.ok(topTracksList);
  }

  @PostMapping("/playlists/{username}")
  public ResponseEntity<AppPlaylist> saveGeneratedPlaylist(
    @PathVariable String username,
    @RequestBody AppPlaylist playlist
  ) {
    AppPlaylist newPlaylist = new AppPlaylist(playlist.getAppTracks());
    newPlaylist.setName(playlist.getName());

    logger.info(
      "Saving playlist id: {}, name: {} to {}'s playlist collection",
      newPlaylist.getId(),
      newPlaylist.getName(),
      username
    );

    // add playlist to user's playlists
    userService.addPlaylist(username, newPlaylist);

    return ResponseEntity.ok(playlist);
  }

  @GetMapping("/playlists/{username}")
  public ResponseEntity<List<AppPlaylist>> showUserPlaylists(
    @PathVariable String username
  ) {
    // fetch from redis
    AppUser user = userService.getUser(username);
    List<AppPlaylist> userPlaylists = user.getPlaylists();
    return ResponseEntity.ok(userPlaylists);
  }

  @PostMapping(path = "/playlists/{username}/{playlistId}")
  public ResponseEntity<AppPlaylist> renameUserPlaylist(
    @PathVariable String username,
    @PathVariable String playlistId,
    @RequestBody Map<String, String> newPlaylistMap
  ) {
    String newPlaylistName = newPlaylistMap.get("newPlaylistName");
    userService.renamePlaylist(username, newPlaylistName, playlistId);

    logger.info(
      "{}'s playlist id: {} renamed to {}",
      username,
      playlistId,
      newPlaylistName
    );

    AppPlaylist playlist = userService.getUserPlaylist(username, playlistId);

    return ResponseEntity.ok(playlist);
  }

  @DeleteMapping("/playlists/{username}/{playlistId}")
  public ResponseEntity<List<AppPlaylist>> deletePlaylist(
    @PathVariable String username,
    @PathVariable String playlistId
  ) {
    userService.deletePlaylist(username, playlistId);
    logger.info("{}'s playlist id: {} has been deleted", username, playlistId);

    AppUser user = userService.getUser(username);
    List<AppPlaylist> userPlaylists = user.getPlaylists();

    return ResponseEntity.ok(userPlaylists);
  }

  @GetMapping("/playlists/{username}/{playlistId}")
  public ResponseEntity<AppPlaylist> showExistingPlaylist(
    @PathVariable String username,
    @PathVariable String playlistId
  ) {
    AppPlaylist playlist = userService.getUserPlaylist(username, playlistId);
    return ResponseEntity.ok(playlist);
  }
}
