package vttp.ssf.spotifyplaylistmaker.models;

import org.apache.commons.lang3.time.DurationFormatUtils;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class SpTrack {

  String title;
  String artist;
  String albumTitle;
  String albumImgUrl;
  int duration;
  String durationStr;
  String trackUrl;

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

  public String getAlbum() {
    return albumTitle;
  }

  public void setAlbum(String album) {
    this.albumTitle = album;
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

  public static SpTrack createFromTrack(Track track) {
    SpTrack spt = new SpTrack();
    spt.setTitle(track.getName());
    spt.setArtist(track.getArtists()[0].getName());
    spt.setAlbum(track.getAlbum().getName());
    spt.setAlbumImgUrl(track.getAlbum().getImages()[0].getUrl());
    spt.setDuration(track.getDurationMs());
    spt.setDurationStr(
      DurationFormatUtils.formatDuration(track.getDurationMs(), "mm:ss")
    );
    spt.setTrackUrl(track.getExternalUrls().get("spotify"));
    return spt;
  }
}
