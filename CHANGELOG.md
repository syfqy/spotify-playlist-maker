## [0.6.1](https://github.com/syfqy/spotify-playlist-maker/compare/v0.6.0...v0.6.1) (2022-09-22)


### Bug Fixes

* fix issue where playlist name not appearing after rename ([632688c](https://github.com/syfqy/spotify-playlist-maker/commit/632688c005c812f6e85d719bd52c3c418b1233b3))



# [0.6.0](https://github.com/syfqy/spotify-playlist-maker/compare/v0.5.0...v0.6.0) (2022-09-22)


### Features

* display no of playlists containing each track ([9c8c81d](https://github.com/syfqy/spotify-playlist-maker/commit/9c8c81d1451b94af2e99f6f8897b910b68cfaf7e))



# [0.5.0](https://github.com/syfqy/spotify-playlist-maker/compare/v0.4.0...v0.5.0) (2022-09-04)


### Bug Fixes

* add form validation on get user's playlists ([0c26b9a](https://github.com/syfqy/spotify-playlist-maker/commit/0c26b9adec32ff1d7065bf8ca6e5b20a8b75c12d))
* enable all forms to be submitted using enter key ([15106a7](https://github.com/syfqy/spotify-playlist-maker/commit/15106a780022f5d4a3d0aa4e4fa4d1c5f6428de3))
* resolve scheduler on request new token not working ([f5fd142](https://github.com/syfqy/spotify-playlist-maker/commit/f5fd1424965afaa6aa840f62013cecd5a186c2ee))


### Features

* add endpoints to rest controller ([fdbbd5c](https://github.com/syfqy/spotify-playlist-maker/commit/fdbbd5c76dae968c93849fe41b8bbb3ac234d21e))
* add scheduler to refresh access token after expiry every 60 min ([384ea8b](https://github.com/syfqy/spotify-playlist-maker/commit/384ea8b80249e9bf2fefe22797108fb766c0013e))



# [0.4.0](https://github.com/syfqy/spotify-playlist-maker/compare/v0.3.0...v0.4.0) (2022-08-25)


### Bug Fixes

* resolve bug where save and edit button only works for first playlist ([4779880](https://github.com/syfqy/spotify-playlist-maker/commit/47798802b867992b952258119e2185ace3df9ae7))
* resolve generated playlist getting appended when search multiple times ([1f620d0](https://github.com/syfqy/spotify-playlist-maker/commit/1f620d0de551bf4b5ef9f430199368e7ad7875c2))
* resolve user playlists getting appended multiple times ([c11a219](https://github.com/syfqy/spotify-playlist-maker/commit/c11a2192dfb213fc9ddb672a2b4d2acc074d9230))


### Features

* add labels to track cards ([bb7883c](https://github.com/syfqy/spotify-playlist-maker/commit/bb7883c4893aa5e7a3e97023c581222ff799046b))
* add links to playlists ([1bba2a3](https://github.com/syfqy/spotify-playlist-maker/commit/1bba2a3d3cdc67e41c767db701ce82e0b28c356c))
* add serialization/deserialization to redis config ([f47b3ab](https://github.com/syfqy/spotify-playlist-maker/commit/f47b3ab5af8e099f28da6e47bb1e3f6b199d1161))
* add ui behaviour on rename and save playlists ([6fb4275](https://github.com/syfqy/spotify-playlist-maker/commit/6fb42750e453d2691ab987b2c639ba37aadc3aa2))
* add view to user's existing playlist ([014073b](https://github.com/syfqy/spotify-playlist-maker/commit/014073bc04e663ea5e6bfe0422768b421233648c))
* allow for renaminof playlists ([2daf204](https://github.com/syfqy/spotify-playlist-maker/commit/2daf204dcd41304bb81fa326cc02a0d8e90e41f4))
* enable deleting of playlists ([5d24874](https://github.com/syfqy/spotify-playlist-maker/commit/5d248747211ed0f4151a513c13d890197e0f3d9f))



# [0.3.0](https://github.com/syfqy/spotify-playlist-maker/compare/v0.2.0...v0.3.0) (2022-08-22)


### Bug Fixes

* add refs to nav bar links ([ff97053](https://github.com/syfqy/spotify-playlist-maker/commit/ff97053f4e42d9d72610a3f4ec15cb80d99f64f2))
* post generated playlist to controller ([d8311fa](https://github.com/syfqy/spotify-playlist-maker/commit/d8311fa686d0cfd801f7a24cdecaa28654519632))
* show spinner on subsequent searches ([4bd1819](https://github.com/syfqy/spotify-playlist-maker/commit/4bd1819756d580a7892d2dc3cc706e36a48d1d68))


### Features

* add attributes to AppPlaylist class ([177545c](https://github.com/syfqy/spotify-playlist-maker/commit/177545c00ff0c3f58c4f977dac83bef21c157c94))
* add basic track cards ([db664b4](https://github.com/syfqy/spotify-playlist-maker/commit/db664b4500e56564f008abad134d9fb9a1283220))
* add basic ui to manage playlists view ([e2d1e37](https://github.com/syfqy/spotify-playlist-maker/commit/e2d1e375d954e998b77af8140c889ac802bbf6b7))
* add custom track and playlist objects ([fc0ede5](https://github.com/syfqy/spotify-playlist-maker/commit/fc0ede52dcc8deacd262e54c2710672b6ef54c25))
* add fetch user playlist on entering username ([e46c45f](https://github.com/syfqy/spotify-playlist-maker/commit/e46c45f9aa120fc8fbd1d7307eef2644be5be2bf))
* add methods to handle ui changes on saving playlist ([1ca8db5](https://github.com/syfqy/spotify-playlist-maker/commit/1ca8db5aa8ac026377686b641415b1122d7f41ff))
* add navbar and main on home page ([1619432](https://github.com/syfqy/spotify-playlist-maker/commit/161943261060c66dc0ad5eecdf59ce251d8ecc0d))
* add rename and remove buttons to playlists ui ([f8301e0](https://github.com/syfqy/spotify-playlist-maker/commit/f8301e065a4a21d5244da847e23ea80753906510))
* add spinner and fetch to search button ([00dbe4d](https://github.com/syfqy/spotify-playlist-maker/commit/00dbe4d08f8bde9ab255e45c946e89ecfc6b4568))
* add User class to represent user ([7509cf7](https://github.com/syfqy/spotify-playlist-maker/commit/7509cf77ec1a200b97f4743a171e13937f9bb65e))
* enable save and retrieval of playlists from db ([fe5897a](https://github.com/syfqy/spotify-playlist-maker/commit/fe5897abb5d1f8e100be7791215157eb95073442))



