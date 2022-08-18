package vttp.ssf.spotifyplaylistmaker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vttp.ssf.spotifyplaylistmaker.models.AppPlaylist;
import vttp.ssf.spotifyplaylistmaker.services.PlaylistMakerService;

@RestController
@RequestMapping("/api")
public class PlaylistMakerRestController {

  @Autowired
  PlaylistMakerService plmService;

  private static final Logger logger = LoggerFactory.getLogger(
    PlaylistMakerRestController.class
  );

  @GetMapping("/search-playlists")
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
  // TODO: post method, save playlist to redis
}
