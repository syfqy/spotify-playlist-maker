package vttp.ssf.spotifyplaylistmaker.models;

import java.util.List;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class AppPlaylist {

  int totalDuration;
  String totalDurationStr;
  List<AppTrack> appTracks;
  int nTracks;

  // TODO: add playlistName attr

  public AppPlaylist() {}

  //SMELL: methods in constructor, does not apply when default constructor used
  public AppPlaylist(List<AppTrack> appTracks) {
    this.appTracks = appTracks;
    this.totalDuration =
      appTracks.stream().map(t -> t.getDuration()).reduce(0, Integer::sum);
    this.totalDurationStr =
      DurationFormatUtils.formatDuration(this.totalDuration, "HH:mm:ss", true);
    this.nTracks = appTracks.size();
  }

  public int getTotalDuration() {
    return totalDuration;
  }

  public void setTotalDuration(int totalDuration) {
    this.totalDuration = totalDuration;
  }

  public List<AppTrack> getAppTracks() {
    return appTracks;
  }

  public void setAppTracks(List<AppTrack> appTracks) {
    this.appTracks = appTracks;
  }

  public String getTotalDurationStr() {
    return totalDurationStr;
  }

  public void setTotalDurationStr(String totalDurationStr) {
    this.totalDurationStr = totalDurationStr;
  }

  public int getnTracks() {
    return nTracks;
  }

  public void setnTracks(int nTracks) {
    this.nTracks = nTracks;
  }
}
