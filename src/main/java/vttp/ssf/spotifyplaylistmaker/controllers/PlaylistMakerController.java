package vttp.ssf.spotifyplaylistmaker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.ssf.spotifyplaylistmaker.services.PlaylistMakerService;

@RestController
@RequestMapping(path="create-playlist")
public class PlaylistMakerController {

    @Autowired
    PlaylistMakerService plmService;

    private static final Logger logger = LoggerFactory.getLogger(PlaylistMakerController.class);

    @GetMapping("/{keyword}")
    public void searchPlaylists(@PathVariable String keyword) {

        plmService.getTopTracksOfKeyword(keyword);

    }
    
    
}
