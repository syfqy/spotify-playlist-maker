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

function enableRename(nameInput) {
    nameInput.prop("readonly", "");
    nameInput.select();
}

function disableRename(nameInput) {
    nameInput.prop("readonly", "readonly");
}

function showSaveBtn(btn) {
    const saveBtn = `<button type="submit" class="btn save-btn btn-primary py-3"
    onclick="savePlaylist(event)"><i class="bi bi-save"></i></button>`
    btn.replaceWith(saveBtn);
}

function showRenameBtn(btn) {
    const renameBtn = `<button type="button" class="btn rename-btn btn-outline-primary py-3"
    onclick="renamePlaylist(event)"><i class="bi bi-pencil"></i></button>`
    btn.replaceWith(renameBtn);
}

function renamePlaylist(e) {
    const btn = $(e.currentTarget);
    const nameInput = btn.siblings(".card").find(".playlist-name-input");
    enableRename(nameInput);
    showSaveBtn(btn);
}

function savePlaylist(e) {
    const btn = $(e.currentTarget);
    const nameInput = btn.siblings(".card").find(".playlist-name-input");
    const form = nameInput.parent("form");

    disableRename(nameInput);
    showRenameBtn(btn);
    updatePlaylist(form);
}

function updatePlaylist(form) {

    const username = $("#username-input")[0].value;
    const playlistId = form.children(".playlist-id").val();
    const url = "/playlists/" + username + "/" + playlistId;

    const formElement = form.get(0);

    // submit form
    fetch(url, {
        method: formElement.method,
        body: new FormData(formElement)
    })
        .then((response) => {
            return response.text();
            // }).then((html) => {
            //     $("#save-playlist-container").replaceWith(html);
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })

}

