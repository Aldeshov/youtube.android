package com.aldeshov.youtube.service.models.api.content

import com.aldeshov.youtube.service.models.api.additional.Copyright
import com.aldeshov.youtube.service.models.api.applications.Channel

data class VideoContent(
    var code: String = "",
    var title: String = "",
    var created_on: String = "",
    var video: String = "",
    var views: String = "",
    var likes: Int = -1,
    var dislikes: Int = -1,
    var preview: String = "",
    var type: VideoContentType = VideoContentType.Gaming,
    var description: String = "",
    var on_channel: Channel = Channel(),
    var copyrights: Copyright = Copyright()
)