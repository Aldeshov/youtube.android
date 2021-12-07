package com.aldeshov.youtube.service.repositories

import com.aldeshov.youtube.service.models.api.applications.Channel
import com.aldeshov.youtube.service.models.api.applications.SubscribeStatus

interface ChannelRepository {
    fun getSubscribeStatus(
        code: String, onResult: (isSuccess: Boolean, response: SubscribeStatus?) -> Unit
    )

    fun subscribe(
        code: String, onResult: (isSuccess: Boolean) -> Unit
    )

    fun unsubscribe(
        code: String, onResult: (isSuccess: Boolean) -> Unit
    )

    fun myChannel(
        onResult: (isSuccess: Boolean, response: Channel?) -> Unit
    )
}
