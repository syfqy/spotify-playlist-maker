package vttp.ssf.spotifyplaylistmaker.controllers;

import java.time.LocalDate;
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
import vttp.ssf.spotifyplaylistmaker.models.AppUser;
import vttp.ssf.spotifyplaylistmaker.services.PlaylistMakerService;
import vttp.ssf.spotifyplaylistmaker.services.UserService;

@Controller
public class PlaylistMakerController {

  @Autowired
  PlaylistMakerService plmService;

  @Autowired
  UserService userService;

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
    // TEST: get nTracks and nPlaylists via user input
    int nTracks = 10;
    int nPlaylists = 5;

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

    model.addAttribute("topTracksList", topTracksList);

    return "frag/generatedPlaylist";
  }

  @PostMapping("/playlists/{username}")
  public String savePlaylist(
    @PathVariable String username,
    @ModelAttribute("topTracksList") AppPlaylist playlist,
    Model model
  ) {
    playlist.setDateCreated(LocalDate.now().toString());
    playlist.setnTracks(playlist.getAppTracks().size());

    logger.info(
      "Saving playlist {} containing: {}",
      playlist.getName(),
      playlist.getnTracks()
    );

    // add playlist to user's playlists
    userService.savePlaylist(username, playlist);

    return "frag/savedPlaylist";
  }

  // TODO: move to separate controller
  @GetMapping("/playlists")
  public String showGuestPlaylists(Model model) {
    return "managePlaylists";
  }

  // TODO: move to separate controller
  @GetMapping("/playlists/{username}")
  public String showUserPlaylists(@PathVariable String username, Model model) {
    // fetch from redis
    AppUser user = userService.getUser(username);
    model.addAttribute("user", user);
    return "frag/userPlaylists";
  }
}
