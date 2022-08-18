package vttp.ssf.spotifyplaylistmaker.models;

import java.util.List;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class AppPlaylist {

  public String name;
  public int nTracks;
  public String dateCreated;
  public int totalDuration;
  public String totalDurationStr;
  public List<AppTrack> appTracks;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getnTracks() {
    return nTracks;
  }

  public void setnTracks(int nTracks) {
    this.nTracks = nTracks;
  }

  public String getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  public int getTotalDuration() {
    return totalDuration;
  }

  public void setTotalDuration(int totalDuration) {
    this.totalDuration = totalDuration;
  }

  public String getTotalDurationStr() {
    return totalDurationStr;
  }

  public void setTotalDurationStr(String totalDurationStr) {
    this.totalDurationStr = totalDurationStr;
  }

  public List<AppTrack> getAppTracks() {
    return appTracks;
  }

  public void setAppTracks(List<AppTrack> appTracks) {
    this.appTracks = appTracks;
  }
}
