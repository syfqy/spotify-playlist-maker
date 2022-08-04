package vttp.ssf.spotifyplaylistmaker.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchPlaylistsRequest;

@Service
public class PlaylistMakerService {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistMakerService.class);

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(
            "BQBfmRveS8h_drR59BZV5ywlwUouRPdYUcL9KtftTgCQ7SfT45J0NT3hAjdfZX4in4kTzGpVBjNHHE1Y9mA6dm0lOJ-ZaLPx6IPp1oO13VMS3ATRFwofRGmPg6qFWh9PW-R_a3YFnE6jawTTpAFQNh1K0rJ-Gw3HaQbMhoY")
            .build();

    private List<String> searchPlaylistsWithKeyword(String keyword) {

        // TODO: change limit to 10
        final SearchPlaylistsRequest spRequest = spotifyApi.searchPlaylists(keyword).limit(20).build();

        try {
            final Paging<PlaylistSimplified> playlistSimplifiedPaging = spRequest.execute();
            final PlaylistSimplified[] resultPlaylistArr = playlistSimplifiedPaging.getItems();
            List<String> plIdList = Arrays.stream(resultPlaylistArr).map(pl -> pl.getId()).toList();
            return plIdList;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            logger.error("Error: " + e.getMessage());
            return new LinkedList<String>();
        }
    }

    private PlaylistTrack[] searchPlaylistById(String playlistId) {

        final GetPlaylistsItemsRequest gpiRequest = spotifyApi.getPlaylistsItems(playlistId).build();

        try {
            final Paging<PlaylistTrack> playlistTrackPaging = gpiRequest.execute();
            final PlaylistTrack[] pltArr = playlistTrackPaging.getItems();
            
            return pltArr;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            return new PlaylistTrack[0];
        }

    }

    private List<String> getTrackIds(PlaylistTrack[] plTrackArr) {

        List<String> trackIdsList = new LinkedList<>();
        for (PlaylistTrack plt : plTrackArr) {
            Track track = (Track) plt.getTrack();
            // skip tracks with null ids (?)
            // if (track.getId() != null) {
                // trackIdsList.add(track.getId() + "_" + track.getName() + "_" + track.getArtists()[0].getName());
            trackIdsList.add(track.getName() + "-" + track.getArtists()[0].getName());
            // }
            
        }
        return trackIdsList;
    }

    private Map<String, Long> rankTracksByFrequency(List<String> trackIdsList) {

        Map<String, Long> trackFreqMap = trackIdsList.stream()
                .collect(groupingBy(t -> t, counting()));

        return trackFreqMap;
    }

    private void getTopNTracks(Map<String, Long> trackFreqMap) {

    Map<String, Long> topTracksMap = trackFreqMap.entrySet()
                .stream()
                .sorted(Entry.<String, Long>comparingByValue().reversed())
                .limit(20)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println(topTracksMap);

    }

    public void getTopTracksOfKeyword(String keyword) {
        List<String> playlistIdList = searchPlaylistsWithKeyword(keyword);
        List<PlaylistTrack[]> playlistTrackArr = playlistIdList.stream().map(plId -> searchPlaylistById(plId)).toList();
        List<List<String>> trackIdsNestedList = playlistTrackArr.stream().map(plt -> getTrackIds(plt)).toList();
        List<String> trackIdsList = trackIdsNestedList.stream().flatMap(Collection::stream).collect(Collectors.toList());
        Map<String, Long> trackFreqMap = rankTracksByFrequency(trackIdsList);
        getTopNTracks(trackFreqMap);
    }

}