package vttp.ssf.spotifyplaylistmaker.models;

import java.util.List;

public class AppUser {

  public String username;
  public List<AppPlaylist> playlists;

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
