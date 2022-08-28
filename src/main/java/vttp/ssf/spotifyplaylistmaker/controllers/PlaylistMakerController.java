package vttp.ssf.spotifyplaylistmaker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  // TODO: handle when search term is invalid and returns no results
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

  @PostMapping(
    path = "/playlists/{username}/{playlistId}",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public String renameUserPlaylist(
    @PathVariable String username,
    @PathVariable String playlistId,
    @RequestParam MultiValueMap<String, String> formData,
    Model model
  ) {
    String newPlaylistName = formData.get("playlist-name").get(0);

    userService.renamePlaylist(username, newPlaylistName, playlistId);

    logger.info(
      "{}'s playlist id: {} renamed to {}",
      username,
      playlistId,
      newPlaylistName
    );

    AppUser user = userService.getUser(username);
    AppPlaylist playlist = userService.getUserPlaylist(username, playlistId);

    model.addAttribute("user", user);
    model.addAttribute("playlist", playlist);

    return "frag/userPlaylists :: playlist-link";
  }

  @DeleteMapping("/playlists/{username}/{playlistId}")
  public String deletePlaylist(
    @PathVariable String username,
    @PathVariable String playlistId,
    Model model
  ) {
    userService.deletePlaylist(username, playlistId);
    logger.info("{}'s playlist id: {} has been deleted", username, playlistId);

    AppUser user = userService.getUser(username);
    model.addAttribute("user", user);

    return "frag/userPlaylists";
  }

  @GetMapping("/playlists/{username}/{playlistId}")
  public String showExistingPlaylist(
    @PathVariable String username,
    @PathVariable String playlistId,
    Model model
  ) {
    AppPlaylist playlist = userService.getUserPlaylist(username, playlistId);
    model.addAttribute("playlist", playlist);
    return "playlist";
  }
}
