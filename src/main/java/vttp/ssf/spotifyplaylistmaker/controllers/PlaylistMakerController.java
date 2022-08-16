package vttp.ssf.spotifyplaylistmaker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/search-playlists")
  public String showGeneratedPlaylist(
    @RequestParam String keyword,
    Model model
  ) {
    logger.info(
      "Generating playlist of 20 tracks via mining 20 playlists about: {}",
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
}
