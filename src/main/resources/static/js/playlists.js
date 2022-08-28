function showUserPlaylists(e) {
    const form = $(e.currentTarget).parents("form")[0];
    const username = $("#username-input")[0].value
    if (form.checkValidity()) {
        fetch("/playlists/" + username)
            .then((response) => {
                return response.text();
            }).then((html) => {
                $("#user-playlists").replaceWith(html);
            }).catch((err) => {
                console.warn("Something went wrong", err);
            })
        e.preventDefault();
    }
}

function enableRename(nameLink, nameInput) {
    nameLink.prop("hidden", "hidden")
    nameInput.prop("hidden", "");
    nameInput.select();
}

function disableRename(nameLink, nameInput) {
    nameLink.prop("hidden", "")
    nameInput.prop("hidden", "hidden");
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

// SMELL: rename and save fns contain duplicated code
function renamePlaylist(e) {
    const btn = $(e.currentTarget);
    const nameLink = btn.siblings(".card").find(".playlist-link");
    const nameInput = btn.siblings(".card").find(".playlist-name-input");
    enableRename(nameLink, nameInput);
    showSaveBtn(btn);
}

function savePlaylist(e) {
    const btn = $(e.currentTarget);
    const nameLink = btn.siblings(".card").find(".playlist-link");
    const nameInput = btn.siblings(".card").find(".playlist-name-input");
    const form = btn.parents("form");

    disableRename(nameLink, nameInput);
    showRenameBtn(btn);
    updatePlaylist(nameLink, form, e);
}

// SMELL: update and delete fns contain duplicated code
function updatePlaylist(nameLink, form, e) {

    const username = $("#username-input")[0].value;
    const playlistId = form.find(".playlist-id").val();
    const url = "/playlists/" + username + "/" + playlistId;

    const formElement = form.get(0);

    fetch(url, {
        method: formElement.method,
        body: new FormData(formElement)
    })
        .then((response) => {
            return response.text();
        }).then((html) => {
            nameLink.replaceWith(html);
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })

    e.preventDefault();

}

function deletePlaylist(e) {

    const btn = $(e.currentTarget);
    const form = btn.parents("form");
    const username = $("#username-input")[0].value;
    const playlistId = form.find(".playlist-id").val();
    const url = "/playlists/" + username + "/" + playlistId;

    fetch(url, {
        method: "delete",
    })
        .then((response) => {
            return response.text();
        }).then((html) => {
            $("#user-playlists").replaceWith(html);
        }).catch((err) => {
            console.warn("Something went wrong", err);
        })

}