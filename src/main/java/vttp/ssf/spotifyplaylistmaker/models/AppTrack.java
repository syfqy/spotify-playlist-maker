package vttp.ssf.spotifyplaylistmaker.models;

import java.io.Serializable;
import org.apache.commons.lang3.time.DurationFormatUtils;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class AppTrack implements Serializable {

  String title;
  String artist;
  String albumTitle;
  String albumImgUrl;
  int duration;
  String durationStr;
  String trackUrl;

  //TODO: add rank and no of times appear in public playlists

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getAlbumTitle() {
    return albumTitle;
  }

  public void setAlbumTitle(String albumTitle) {
    this.albumTitle = albumTitle;
  }

  public String getAlbumImgUrl() {
    return albumImgUrl;
  }

  public void setAlbumImgUrl(String albumImgUrl) {
    this.albumImgUrl = albumImgUrl;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getDurationStr() {
    return durationStr;
  }

  public void setDurationStr(String durationStr) {
    this.durationStr = durationStr;
  }

  public String getTrackUrl() {
    return trackUrl;
  }

  public void setTrackUrl(String trackUrl) {
    this.trackUrl = trackUrl;
  }

  public static AppTrack createFromTrack(Track track) {
    AppTrack t = new AppTrack();
    t.setTitle(track.getName());
    t.setArtist(track.getArtists()[0].getName());
    t.setAlbumTitle(track.getAlbum().getName());
    t.setAlbumImgUrl(track.getAlbum().getImages()[0].getUrl());
    t.setDuration(track.getDurationMs());
    t.setDurationStr(
      DurationFormatUtils.formatDuration(track.getDurationMs(), "mm:ss")
    );
    t.setTrackUrl(track.getExternalUrls().get("spotify"));
    return t;
  }
}
