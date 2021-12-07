package com.aldeshov.youtube.ui.activity.main.drawer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.api.applications.Channel
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.service.repositories.ChannelRepository

class DrawerViewModel(private val channelRepository: ChannelRepository) : BaseComposeViewModel() {
    var channel by mutableStateOf(Channel())

    fun getMyChannel() {
        status = LiveStatus.LOADING
        channelRepository.myChannel { isSuccess, response ->
            if (isSuccess) {
                if (response != null) {
                    channel = response
                    status = LiveStatus.LOADED_SUCCESSFUL
                }
                else {
                    status = LiveStatus.LOADED_EMPTY
                }
            } else {
                status = LiveStatus.ERROR
                message = "Couldn't get channel from repository! please try again later!"
            }
        }
    }
}