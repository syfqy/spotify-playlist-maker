package vttp.ssf.spotifyplaylistmaker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp.ssf.spotifyplaylistmaker.models.AppPlaylist;
import vttp.ssf.spotifyplaylistmaker.models.AppTrack;
import vttp.ssf.spotifyplaylistmaker.services.PlaylistMakerService;

@Controller
public class PlaylistMakerController {

  @Autowired
  PlaylistMakerService plmService;

  private static final Logger logger = LoggerFactory.getLogger(
    PlaylistMakerController.class
  );

  @GetMapping("/")
  public String showHomePage() {
    return "index";
  }

  @GetMapping("/search")
  public String showGeneratedPlaylist(
    @RequestParam String keyword,
    Model model
  ) {
    // TODO: remove in production
    int nTracks = 10;
    int nPlaylists = 5;

    logger.info(
      "Generating playlist of {} tracks via mining {} playlists about: {}",
      nTracks,
      nPlaylists,
      keyword
    );

    AppPlaylist topTracksList = plmService.getTopTracksOfKeyword(
      10,
      10,
      keyword
    );

    model.addAttribute("topTracksList", topTracksList);

    return "frag/generatedPlaylist :: playlist-container";
  }

  // TODO: save to redis db
  @PostMapping("/playlists")
  public String savePlaylist(
    @ModelAttribute("topTracksList") AppPlaylist playlist,
    Model model
  ) {
    // TODO: remove in production
    logger.info("Saving playlist containing: {}", playlist.getnTracks());
    for (AppTrack track : playlist.getAppTracks()) {
      logger.info(track.getTitle() + " by" + track.getArtist());
      break;
    }

    // call plmService
    return "frag/savedPlaylist";
  }

  @GetMapping("/{username}/playlists")
  public String showUserPlaylists(@PathVariable String username) {
    return null;
  }
}
