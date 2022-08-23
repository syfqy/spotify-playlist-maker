// replace identifiers with callers

function showUserPlaylists() {

    // BUG: double loading of playlists when focusout triggered multiple times, consider replacing with button instead

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
    $("#playlist-name-input").prop("readonly", "");
    $("#playlist-name-input")[0].select();

}

function disableRename() {
    $("#playlist-name-input").prop("readonly", "readonly");
}

function showSaveBtn() {
    const btn = $("#rename-save-btn");
    const saveBtn = `<button type="submit" id="rename-save-btn" class="btn save-btn btn-primary py-3"
    onclick="savePlaylist()"><i class="bi bi-save"></i></button>`
    btn.replaceWith(saveBtn);
}

function showRenameBtn() {
    const btn = $("#rename-save-btn");
    const renameBtn = `<button type="button" id="rename-save-btn" class="btn rename-btn btn-outline-primary py-3"
    onclick="renamePlaylist()"><i class="bi bi-pencil"></i></button>`
    btn.replaceWith(renameBtn);
}

function renamePlaylist() {
    enableRename();
    showSaveBtn();
}

function savePlaylist() {
    disableRename();
    showRenameBtn();
    updatePlaylist()
}

function updatePlaylist() {

    const form = $("#playlist-form")[0];
    const username = $("#username-input")[0].value
    const playlistId = $("#playlist-id")[0].value
    const url = "/playlists/" + username + "/" + playlistId;

    // submit form
    fetch(url, {
        method: form.method,
        body: new FormData(form)
    })
        .then((response) => {
            return response.text();
            // }).then((html) => {
            //     $("#save-playlist-container").replaceWith(html);
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })

}

