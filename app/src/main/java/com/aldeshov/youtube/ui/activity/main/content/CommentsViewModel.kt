package com.aldeshov.youtube.ui.activity.main.content

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.api.content.Comment
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.service.repositories.ContentRepository

class CommentsViewModel(private val contentRepository: ContentRepository) : BaseComposeViewModel() {
    var videoContentCode by mutableStateOf("")

    var comments by mutableStateOf(listOf<Comment>())
    var comment by mutableStateOf(TextFieldValue(""))
        private set

    fun fetchComments() {
        status = LiveStatus.LOADING
        contentRepository.getVideoContentComments(videoContentCode) { isSuccess, response ->
            if (isSuccess) {
                if (response != null && response.isNotEmpty()) {
                    status = LiveStatus.LOADED_SUCCESSFUL
                    comments = response.toList()
                } else
                    status = LiveStatus.LOADED_EMPTY
            } else {
                status = LiveStatus.ERROR
                message = "Couldn't load comments from repository! please try again later!"
            }
        }
    }

    fun sendComment() {
        status = LiveStatus.LOADING
        contentRepository.addVideoContentComment(
            videoContentCode,
            comment.text
        ) { isSuccess, response ->
            if (isSuccess && response != null) {
                status = LiveStatus.NOTHING
                comment = TextFieldValue("")
            } else {
                status = LiveStatus.ERROR
                message = "Could not send comment!"
            }
        }
    }

    fun updateComment(comment: TextFieldValue) {
        this.comment = comment
    }
}