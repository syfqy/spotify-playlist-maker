function showUserPlaylists() {

    const username = $("#username-input")[0].value

    // async GET request to fetch user's cart
    fetch("/playlists/" + username)
        .then((response) => {
            return response.text();
        }).then((html) => {
            $("#main").append(html);
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })

}
