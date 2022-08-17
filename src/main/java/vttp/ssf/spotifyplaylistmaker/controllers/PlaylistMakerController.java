package vttp.ssf.spotifyplaylistmaker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp.ssf.spotifyplaylistmaker.models.SpTrack;
import vttp.ssf.spotifyplaylistmaker.models.SpTrackList;
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
    int nTracks = 10;
    int nPlaylists = 10;

    logger.info(
      "Generating playlist of {} tracks via mining {} playlists about: {}",
      nTracks,
      nPlaylists,
      keyword
    );

    SpTrackList topTracksList = plmService.getTopTracksOfKeyword(
      keyword,
      10,
      10
    );

    model.addAttribute("topTracksList", topTracksList);

    return "frag/generatedPlaylist :: playlist-container";
  }

  // TODO: save to redis db
  @PostMapping("/playlists")
  public String savePlaylist(
    @ModelAttribute("topTracksList") SpTrackList playlist,
    Model model
  ) {
    // REMOVE
    logger.info("Saving playlist containing: {}", playlist.getnTracks());
    for (SpTrack track : playlist.getSpTracks()) {
      logger.info(track.getTitle() + " by" + track.getArtist());
    }
    return "save";
  }
}
