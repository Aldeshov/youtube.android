package com.aldeshov.youtube.ui.activity.main.content

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.api.applications.SubscribeStatusType
import com.aldeshov.youtube.service.models.api.content.LikeStatusType
import com.aldeshov.youtube.service.models.api.content.VideoContent
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.service.repositories.ChannelRepository
import com.aldeshov.youtube.service.repositories.ContentRepository

class ContentViewModel(
    private val contentRepository: ContentRepository,
    private val channelRepository: ChannelRepository,
) : BaseComposeViewModel() {
    var videoContent by mutableStateOf(VideoContent())

    var floatingLikes by mutableStateOf(0)
        private set

    var floatingDislikes by mutableStateOf(0)
        private set

    var likeStatus by mutableStateOf(LikeStatusType.NOTHING)

    var floatingSubscribers by mutableStateOf(0)
        private set

    var subscribeStatus by mutableStateOf(SubscribeStatusType.NOTHING)

    fun fetchContent(code: String) {
        status = LiveStatus.LOADING
        contentRepository.getVideoContent(code) { isSuccess, response ->
            if (isSuccess) {
                if (response != null) {
                    status = LiveStatus.LOADED_SUCCESSFUL
                    videoContent = response
                    floatingLikes = response.likes
                    floatingDislikes = response.dislikes
                    floatingSubscribers = response.on_channel.subscribers
                }
                else
                    status = LiveStatus.LOADED_EMPTY
            } else {
                status = LiveStatus.ERROR
                message = "Couldn't load video content from repository! please try again later!"
            }
        }
    }

    fun fetchLikeStatus() {
        likeStatus = LikeStatusType.FETCHING
        contentRepository.getLikeStatus(videoContent.code) { isSuccess, response ->
            if (isSuccess && response != null)
                likeStatus = LikeStatusType.getByValue(response)!!
        }
    }

    fun fetchSubscribeStatus() {
        subscribeStatus = SubscribeStatusType.FETCHING
        channelRepository.getSubscribeStatus(videoContent.on_channel.code) { isSuccess, response ->
            if (isSuccess && response != null)
                subscribeStatus = SubscribeStatusType.getByValue(response)!!
        }
    }

    fun likeContent() {
        if (likeStatus != LikeStatusType.LIKED) {
            contentRepository.likeVideoContent(videoContent.code) {
                if (it) {
                    if (likeStatus == LikeStatusType.DISLIKED)
                        floatingDislikes -= 1
                    likeStatus = LikeStatusType.LIKED
                    floatingLikes += 1
                }
            }
        }
        else {
            retractLikeDislikeContent()
        }
    }

    fun dislikeContent() {
        if (likeStatus != LikeStatusType.DISLIKED) {
            contentRepository.dislikeVideoContent(videoContent.code) {
                if (it) {
                    if (likeStatus == LikeStatusType.LIKED)
                        floatingLikes -= 1
                    likeStatus = LikeStatusType.DISLIKED
                    floatingDislikes += 1
                }
            }
        }
        else {
            retractLikeDislikeContent()
        }
    }

    private fun retractLikeDislikeContent() {
        contentRepository.retractLikeDislikeVideoContent(videoContent.code) {
            if (it) {
                if (likeStatus == LikeStatusType.LIKED)
                    floatingLikes -= 1
                if (likeStatus == LikeStatusType.DISLIKED)
                    floatingDislikes -= 1
                likeStatus = LikeStatusType.IDLE
            }
        }
    }

    fun subscribeChannel() {
        subscribeStatus = SubscribeStatusType.FETCHING
        channelRepository.subscribe(videoContent.on_channel.code) {
            if (it) {
                subscribeStatus = SubscribeStatusType.SUBSCRIBED
                floatingSubscribers += 1
            }
        }
    }

    fun unsubscribeChannel() {
        subscribeStatus = SubscribeStatusType.FETCHING
        channelRepository.unsubscribe(videoContent.on_channel.code) {
            if (it) {
                subscribeStatus = SubscribeStatusType.IDLE
                floatingSubscribers -= 1
            }
        }
    }
}