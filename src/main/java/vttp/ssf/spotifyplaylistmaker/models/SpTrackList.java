package vttp.ssf.spotifyplaylistmaker.models;

import java.util.List;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class SpTrackList {

  int totalDuration;
  String totalDurationStr;
  List<SpTrack> spTracks;
  int nTracks;

  public SpTrackList() {}

  //SMELL: methods in constructor, does not apply when default constructor used
  public SpTrackList(List<SpTrack> spTracks) {
    this.spTracks = spTracks;
    this.totalDuration =
      spTracks.stream().map(spt -> spt.getDuration()).reduce(0, Integer::sum);
    this.totalDurationStr =
      DurationFormatUtils.formatDuration(this.totalDuration, "HH:mm:ss", true);
    this.nTracks = spTracks.size();
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

  public List<SpTrack> getSpTracks() {
    return spTracks;
  }

  public void setSpTracks(List<SpTrack> spTracks) {
    this.spTracks = spTracks;
  }

  public int getnTracks() {
    return nTracks;
  }

  public void setnTracks(int nTracks) {
    this.nTracks = nTracks;
  }
}
