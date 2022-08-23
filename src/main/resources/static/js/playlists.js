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

function enableRename() {
    $("#playlist-name-input")[0].disabled = false;
    $("#playlist-name-input")[0].select();

}

function disableRename() {
    $("#playlist-name-input")[0].disabled = true;
}

function toggleRenameSaveIcon() {

    const btn = $("#rename-save-btn");

    btn.toggleClass("rename-btn");
    btn.toggleClass("save-btn");
    btn.toggleClass("btn-outline-primary");
    btn.toggleClass("btn-primary");

    const renameIcon = `<i class="bi bi-pencil"></i>`;
    const saveIcon = `<i class="bi bi-save"></i>`;

    const displayIcon = btn.hasClass("rename-btn") ? renameIcon : saveIcon;
    btn.html(displayIcon);
}

function renameSaveHandler() {
    // change style
    toggleRenameSaveIcon();

    const btn = $("#rename-save-btn");
    const isRenameActive = btn.hasClass("rename-btn");

    // enable/disable playlist name input
    const inputHandler = isRenameActive ? disableRename : enableRename;
    inputHandler();

    if (!isRenameActive) {
        // save
    }
}

function savePlaylistName() {

}

