package com.aldeshov.youtube.service.models.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.aldeshov.youtube.service.models.LiveStatus

open class BaseComposeViewModel: ViewModel() {
    var status by mutableStateOf(LiveStatus.NOTHING)
    var message by mutableStateOf("")
}