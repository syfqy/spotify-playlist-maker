function showSpinner() {
    $("#search-playlists-btn").replaceWith(`<div id="spinner" class="spinner-border text-primary" role="status"></div>`);
}

function showSearchBtn() {
    $("#spinner").replaceWith(`<button id="search-playlists-btn" class="btn btn-outline-primary" onclick="searchPlaylists(event)"
    type="submit">Search</button>`);
}

// TODO: move fetching into utils 
function searchPlaylists(e) {

    // get search keyword
    const keyword = $("#search-playlists-input")[0].value;

    // build url
    const searchPlaylistsBaseUrl = "/search?";
    const params = new URLSearchParams({
        "keyword": keyword
    });

    console.log("fetching")
    // request top N tracks
    fetch(searchPlaylistsBaseUrl + params)
        .then((response) => {
            return response.text();
        }).then((html) => {
            showSearchBtn();
            $("#playlist-container").replaceWith(html);
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })
    e.preventDefault();

}

function searchHandler(e) {
    showSpinner();
    searchPlaylists(e);
}

function savePlaylist(e) {

    const form = e.currentTarget;
    const username = $("#username-input")[0].value
    const url = "/playlists/" + username;

    // submit form
    fetch(url, {
        method: form.method,
        body: new FormData(form)
    })
        .then((response) => {
            return response.text();
        }).then((html) => {
            $("#save-playlist-container").replaceWith(html);
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })

    e.preventDefault();
}