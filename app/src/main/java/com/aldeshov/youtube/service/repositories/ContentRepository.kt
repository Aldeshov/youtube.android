package com.aldeshov.youtube.service.repositories

import com.aldeshov.youtube.service.models.api.content.Comment
import com.aldeshov.youtube.service.models.api.content.LikeStatus
import com.aldeshov.youtube.service.models.api.content.VideoContent

interface ContentRepository {
    fun getVideoContents(
        onResult: (isSuccess: Boolean, response: ArrayList<VideoContent>?) -> Unit
    )

    fun getVideoContent(
        code: String, onResult: (isSuccess: Boolean, response: VideoContent?) -> Unit
    )

    fun getVideoContentComments(
        code: String, onResult: (isSuccess: Boolean, response: ArrayList<Comment>?) -> Unit
    )

    fun addVideoContentComment(
        code: String, text: String, onResult: (isSuccess: Boolean, response: Comment?) -> Unit
    )

    fun getLikeStatus(
        code: String, onResult: (isSuccess: Boolean, response: LikeStatus?) -> Unit
    )

    fun likeVideoContent(
        code: String, onResult: (isSuccess: Boolean) -> Unit
    )

    fun dislikeVideoContent(
        code: String, onResult: (isSuccess: Boolean) -> Unit
    )

    fun retractLikeDislikeVideoContent(
        code: String, onResult: (isSuccess: Boolean) -> Unit
    )
}