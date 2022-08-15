package vttp.ssf.spotifyplaylistmaker.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.michaelthelin.spotify.model_objects.specification.Track;
import vttp.ssf.spotifyplaylistmaker.services.PlaylistMakerService;

@RestController
@RequestMapping("/api")
public class PlaylistMakerRestController {

    @Autowired
    PlaylistMakerService plmService;

    private static final Logger logger = LoggerFactory.getLogger(PlaylistMakerRestController.class);

    @GetMapping("/search-playlists")
    public ResponseEntity<List<Track>> searchPlaylists(@RequestParam(name="keyword") String keyword,
            @RequestParam("playlists") int nPlaylists, @RequestParam(name="tracks") int nTracks) {

        logger.info("Generating playlist of {} tracks via mining {} playlists about: {}", nTracks, nPlaylists, keyword);
        List<Track> topTracksList = plmService.getTopTracksOfKeyword(keyword, nTracks, nPlaylists);
        return ResponseEntity.ok(topTracksList);
    }

    // TODO: save playlist to spotify acc

}
