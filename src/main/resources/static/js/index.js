function showSpinner() {
    $("#search-playlists-btn").replaceWith(`<div id="spinner" class="spinner-border text-primary" role="status"></div>`);
}

function showSearchBtn() {
    $("#spinner").replaceWith(`<button id="search-playlists-btn" class="btn btn-outline-primary" onclick="searchPlaylists(event)"
    type="submit">Search</button>`);
}

function searchPlaylists(e) {

    // prevent page refresh
    e.preventDefault();

    // show spinner
    showSpinner();

    // get search keyword
    const keyword = $("#search-playlists-input")[0].value;

    // build url
    const searchPlaylistsBaseUrl = "/search?";
    const params = new URLSearchParams({
        "keyword": keyword
    });

    // request top N tracks
    fetch(searchPlaylistsBaseUrl + params)
        .then((response) => {
            return response.text();
        }).then((html) => {
            showSearchBtn();
            $("#main").append(html);
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })

}

/* 
TODO: logic to save playlist
async submit form to controller
prevent default
change save btn to saved
add <a> tag to view playlists
*/

function savePlaylist() {

}