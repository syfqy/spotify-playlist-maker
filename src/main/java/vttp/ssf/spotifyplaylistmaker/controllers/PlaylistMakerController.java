package vttp.ssf.spotifyplaylistmaker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlaylistMakerController {

  @GetMapping("/")
  public String showHomePage() {
    return "index";
  }

  @GetMapping("/search-playlists")
  public String showGeneratedPlaylist(@RequestParam String keyword) {
    System.out.println("Searching playlists about: " + keyword);
    return "frag/test :: playlist-container";
  }
}
