$(document).ready(() => {

    // wait for username input field to unfocus
    $("#search-playlists-btn").click((e) => {

        // prevent page reload
        e.preventDefault();

        // show spinner
        $("#spinner").removeClass("visually-hidden");

        // get search keyword
        const keyword = $("#search-playlists-input")[0].value;

        // build url
        const searchPlaylistsBaseUrl = "/search-playlists?";
        const params = new URLSearchParams({
            "keyword": keyword
        });

        // request top N tracks
        fetch(searchPlaylistsBaseUrl + params)
            .then((response) => {
                return response.text();
            }).then((html) => {
                $("#spinner").addClass("visually-hidden");
                $("#playlist")[0].innerHTML = html;
            }).catch((err) => {
                console.warn("Something went wrong", err);
            })

    });

});