package com.aldeshov.youtube.service.models.api.applications

enum class SubscribeStatusType(val value: Int = -99) {
    NOTHING,
    FETCHING(-1),
    IDLE(0),
    SUBSCRIBED(1);

    companion object {
        private val VALUES = values()
        private fun toType(value: Boolean): Int { return if (value) 1 else 0 }

        fun getByValue(subscribeStatus: SubscribeStatus) = VALUES.firstOrNull {
            it.value == toType(subscribeStatus.status)
        }
    }
}