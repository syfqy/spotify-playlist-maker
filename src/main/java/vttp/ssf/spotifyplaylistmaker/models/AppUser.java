package vttp.ssf.spotifyplaylistmaker.models;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class AppUser implements Serializable {

  public String username;
  public List<AppPlaylist> playlists;

  public AppUser() {}

  public AppUser(String username) {
    this.username = username;
    this.playlists = new LinkedList<>();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<AppPlaylist> getPlaylists() {
    return playlists;
  }

  public void setPlaylists(List<AppPlaylist> playlists) {
    this.playlists = playlists;
  }
}
