package vttp.ssf.spotifyplaylistmaker.controllers;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vttp.ssf.spotifyplaylistmaker.models.AppPlaylist;
import vttp.ssf.spotifyplaylistmaker.models.AppTrack;
import vttp.ssf.spotifyplaylistmaker.models.AppUser;
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
    // TEST
    logger.info("Saving playlist containing: {}", playlist.getnTracks());
    for (AppTrack track : playlist.getAppTracks()) {
      logger.info(track.getTitle() + " by" + track.getArtist());
      break;
    }

    // call plmService
    return "frag/savedPlaylist";
  }

  @GetMapping("/playlists")
  public String showUserPlaylists(Model model) {
    // TEST
    AppUser user1 = generateTestUser();
    model.addAttribute("user", user1);

    return "testPlaylists";
  }

  private AppUser generateTestUser() {
    // TEST
    AppUser user1 = new AppUser();
    user1.setUsername("user1");

    AppPlaylist pl1 = new AppPlaylist();
    pl1.setName("playlist 1");
    pl1.setDateCreated("12-01-2022");
    pl1.setTotalDurationStr("01:32:15");
    pl1.setnTracks(20);

    AppPlaylist pl2 = new AppPlaylist();
    pl2.setName("playlist 2");
    pl2.setDateCreated("21-11-2020");
    pl2.setTotalDurationStr("02:01:53");
    pl2.setnTracks(15);

    List<AppPlaylist> l = new LinkedList<>();
    l.add(pl1);
    l.add(pl2);

    user1.setPlaylists(l);
    return user1;
  }
}
