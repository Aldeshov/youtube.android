package com.aldeshov.youtube.service.models.api.applications

data class Channel(
    var code: String = "",
    var name: String = "",
    var avatar: String = "",
    var is_verified: Boolean = false,
    var description: String = "",
    var created_date: String = "",
    var subscribers: Int = -1
)