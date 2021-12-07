package com.aldeshov.youtube.service.models.api.content

data class Playlist(
    var key: Int = -1,
    var title: String = "",
    var contents: ArrayList<VideoContent> = ArrayList()
)