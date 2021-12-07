package com.aldeshov.youtube.service.models.api.content

enum class LikeStatusType(val value: Int = -99) {
    NOTHING,
    FETCHING(-1),
    IDLE(0),
    LIKED(1),
    DISLIKED(2);

    companion object {
        private val VALUES = values()
        fun getByValue(likeStatus: LikeStatus) = VALUES.firstOrNull { it.value == likeStatus.status }
    }
}