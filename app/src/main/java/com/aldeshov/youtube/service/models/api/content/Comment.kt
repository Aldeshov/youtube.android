package com.aldeshov.youtube.service.models.api.content

import com.aldeshov.youtube.service.models.api.applications.Channel

data class Comment(
    var owner: Channel = Channel(),
    var text: String = "",
    var content: VideoContent = VideoContent()
)