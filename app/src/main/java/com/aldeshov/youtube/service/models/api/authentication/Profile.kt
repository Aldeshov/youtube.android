package com.aldeshov.youtube.service.models.api.authentication

import com.aldeshov.youtube.service.models.api.applications.Channel
import com.aldeshov.youtube.service.models.api.content.Playlist
import com.aldeshov.youtube.service.models.api.content.VideoContent

data class Profile(
    var is_private: Boolean = false,
    var saved_playlists: ArrayList<Playlist> = ArrayList(),
    var saved_contents: ArrayList<VideoContent> = ArrayList(),
    var subscribed: ArrayList<Channel> = ArrayList(),
)