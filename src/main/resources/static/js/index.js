$(document).ready(() => {

    // wait for username input field to unfocus
    $("#search-playlists-btn").click((e) => {

        e.preventDefault();

        // show spinner
        $("#spinner").removeClass("visually-hidden");

        // get keyword
        const keyword = $("#search-playlists-input")[0].value;
        console.log(keyword);

        // build url
        const searchPlaylistsBaseUrl = "/search-playlists?";
        const params = new URLSearchParams({
            "keyword": keyword
        });

        // request playlists
        fetch(searchPlaylistsBaseUrl + params)
            .then((response) => {
                return response.text();
            }).then((html) => {
                // TEMP: Simulate async 
                setTimeout(
                    () => {
                        $("#spinner").addClass("visually-hidden");
                        $("#playlist")[0].innerHTML = html;
                    }, 3000
                )
            }).catch((err) => {
                console.warn("Something went wrong", err);
            }), 3000

    });

});