package com.aldeshov.youtube.ui.activity.main.contents

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.api.content.VideoContent
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.service.repositories.ContentRepository
import com.aldeshov.youtube.service.repositories.UserRepository

class ListViewModel(
    private val contentRepository: ContentRepository,
    private val userRepository: UserRepository
) : BaseComposeViewModel() {
    var list by mutableStateOf(listOf<VideoContent>())

    var currentItem by mutableStateOf(-1)

    fun fetchContentList() {
        status = LiveStatus.LOADING
        contentRepository.getVideoContents { isSuccess, response ->
            if (isSuccess) {
                if (response != null && response.isNotEmpty()) {
                    status = LiveStatus.LOADED_SUCCESSFUL
                    list = response.toList()
                }
                else
                    status = LiveStatus.LOADED_EMPTY
            } else {
                status = LiveStatus.ERROR
                message = "Couldn't load contents from repository! please try again later!"
            }
        }
    }

    fun fetchFavouriteContentList() {
        status = LiveStatus.LOADING
        userRepository.myProfile { isSuccess, response ->
            if (isSuccess) {
                if (response != null && response.saved_contents.isNotEmpty()) {
                    status = LiveStatus.LOADED_SUCCESSFUL
                    list = response.saved_contents.toList()
                }
                else
                    status = LiveStatus.LOADED_EMPTY
            } else {
                status = LiveStatus.ERROR
                message = "Couldn't get favourite contents from repository! please try again later!"
            }
        }
    }

    fun setCurrentListItem(index : Int) {
        currentItem = index
    }
}