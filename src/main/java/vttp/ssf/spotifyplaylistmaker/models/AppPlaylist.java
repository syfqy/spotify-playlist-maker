package vttp.ssf.spotifyplaylistmaker.models;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class AppPlaylist implements Serializable {

  public String id;
  public String name;
  public int nTracks;
  public String dateCreated;
  public int totalDuration;
  public String totalDurationStr;
  public List<AppTrack> appTracks;

  public AppPlaylist() {
    this.id = generateId();
  }

  //SMELL: methods in constructor, does not apply when default constructor used
  public AppPlaylist(List<AppTrack> appTracks) {
    this.id = generateId();
    this.appTracks = appTracks;
    this.totalDuration =
      appTracks.stream().map(t -> t.getDuration()).reduce(0, Integer::sum);
    this.totalDurationStr =
      DurationFormatUtils.formatDuration(this.totalDuration, "HH:mm:ss", true);
    this.nTracks = appTracks.size();
  }

  private synchronized String generateId() {
    Random r = new Random();
    StringBuilder strBuilder = new StringBuilder();
    while (strBuilder.length() < 4) {
      strBuilder.append(Integer.toHexString(r.nextInt()));
    }
    return strBuilder.toString().substring(0, 4);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
