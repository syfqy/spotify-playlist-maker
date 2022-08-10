package vttp.ssf.spotifyplaylistmaker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlaylistMakerController {

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

}
