package vttp.ssf.spotifyplaylistmaker.services;

import static java.util.stream.Collectors.*;

import com.neovisionaries.i18n.CountryCode;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchPlaylistsRequest;
import vttp.ssf.spotifyplaylistmaker.models.AppPlaylist;
import vttp.ssf.spotifyplaylistmaker.models.AppTrack;

@Service
public class PlaylistMakerService {

  // ******************************
  // * Class attributes
  // ******************************

  @Autowired
  SpotifyApi spotifyApi;

  private static final Logger logger = LoggerFactory.getLogger(
    PlaylistMakerService.class
  );

  // ******************************
  // * Private methods
  // ******************************

  /**
   * Returns a list of playlist ids which are related to the keyword.
   *
   * @param keyword keyword that playlists should contain
   * @return list of playlist IDs containing given keyword
   */
  private List<String> searchPlaylistsByKeyword(
    String keyword,
    int nPlaylists
  ) {
    final SearchPlaylistsRequest spRequest = spotifyApi
      .searchPlaylists(keyword)
      .limit(nPlaylists)
      .market(CountryCode.US)
      .build();

    try {
      final Paging<PlaylistSimplified> playlistSimplifiedPaging = spRequest.execute();
      final PlaylistSimplified[] resultPlaylistArr = playlistSimplifiedPaging.getItems();
      List<String> plIdList = Arrays
        .stream(resultPlaylistArr)
        .map(pl -> pl.getId())
        .toList();
      return plIdList;
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      logger.error("Error: " + e.getMessage());
      return new LinkedList<String>();
    }
  }

  /**
   * Returns array of PlaylistTrack objects of a playlist.
   *
   * @param playlistId unique identifier of playlist
   * @return array of PlaylistTrack objects
   */
  private PlaylistTrack[] getPlaylistTracks(String playlistId) {
    // TODO: move to repo
    final GetPlaylistsItemsRequest gpiRequest = spotifyApi
      .getPlaylistsItems(playlistId)
      .build();

    try {
      final Paging<PlaylistTrack> playlistTrackPaging = gpiRequest.execute();
      final PlaylistTrack[] pltArr = playlistTrackPaging.getItems();
      return pltArr;
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
      return new PlaylistTrack[0];
    }
  }

  private Map<String, Track> getTrackNamesAndArtist2(
    PlaylistTrack[] plTrackArr
  ) {
    Map<String, Track> trackNameMap = new HashMap<String, Track>();

    for (PlaylistTrack plt : plTrackArr) {
      try {
        String key =
          ((Track) plt.getTrack()).getName() +
          "-" +
          ((Track) plt.getTrack()).getArtists()[0].getName();
        Track value = (Track) plt.getTrack();
        if (trackNameMap.get(key) == null) trackNameMap.put(key, value);
      } catch (Exception e) {
        logger.error(e.getMessage());
      }
    }

    return trackNameMap;
  }

  private Map<String, Long> rankTracksByFrequency(List<String> trackList) {
    Map<String, Long> trackFreqMap = trackList
      .stream()
      .collect(groupingBy(t -> t, counting()));

    return trackFreqMap;
  }

  private Map<String, Long> getTopNTracks(
    Map<String, Long> trackFreqMap,
    int nTracks
  ) {
    Map<String, Long> topTracksMap = trackFreqMap
      .entrySet()
      .stream()
      .sorted(Entry.<String, Long>comparingByValue().reversed())
      .limit(nTracks)
      .collect(
        Collectors.toMap(
          Map.Entry::getKey,
          Map.Entry::getValue,
          (e1, e2) -> e1,
          LinkedHashMap::new
        )
      );

    return topTracksMap;
  }

  // ******************************
  // * Public methods
  // ******************************

  // SMELL: long method, a lot of type casting and manipulation
  public AppPlaylist getTopTracksOfKeyword(
    int nTracks,
    int nPlaylists,
    String keyword
  ) {
    // get list of playlistIds related to keyword
    // FIXME: handle when search term is invalid and returns no results
    List<String> playlistIdList = searchPlaylistsByKeyword(keyword, nPlaylists);

    // get list of playlist tracks from playlist ids
    List<PlaylistTrack[]> playlistTrackArr = playlistIdList
      .stream()
      .map(plId -> getPlaylistTracks(plId))
      .toList();

    // get list of trackName-artist: Track maps
    // NOTE: Track name + artist used as unique id as some tracks have missing ids
    List<Map<String, Track>> trackNameMapList = playlistTrackArr
      .stream()
      .map(plt -> getTrackNamesAndArtist2(plt))
      .toList();

    // merge list of maps to single map as lookup table
    Map<String, Track> allTracksMap = trackNameMapList
      .stream()
      .reduce(
        (firstMap, secondMap) -> {
          firstMap.putAll(secondMap);
          return firstMap;
        }
      )
      .orElse(null);

    // get list of trackNames-artists
    List<String> trackNameList = new LinkedList<>(allTracksMap.keySet());

    // rank tracks by freq and get top N tracks
    Map<String, Long> trackFreqMap = rankTracksByFrequency(trackNameList);
    Map<String, Long> topTracksMap = getTopNTracks(trackFreqMap, nTracks);

    // get list of Track objects for top N tracks
    List<AppTrack> appTracks = Arrays
      .stream(topTracksMap.keySet().toArray())
      .map(t -> allTracksMap.get(t))
      .map(t -> AppTrack.createFromTrack(t))
      .toList();

    AppPlaylist topTracksList = new AppPlaylist(appTracks);

    return topTracksList;
  }
}
