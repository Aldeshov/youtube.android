package com.aldeshov.youtube.service.models.api.additional

data class Copyright(
    var is_private: Boolean = false,
    var is_adult_content: Boolean = false,
    var is_kids_content: Boolean = false,
    var song_copyrights: ArrayList<SongCopyright> = ArrayList(),
    var game_copyrights: ArrayList<GameCopyright> = ArrayList()
)