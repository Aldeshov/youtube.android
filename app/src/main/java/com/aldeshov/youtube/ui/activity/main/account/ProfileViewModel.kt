package com.aldeshov.youtube.ui.activity.main.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.api.authentication.Profile
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.service.repositories.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : BaseComposeViewModel() {
    var profile by mutableStateOf(Profile())

    fun getMyProfile() {
        status = LiveStatus.LOADING
        userRepository.myProfile { isSuccess, response ->
            if (isSuccess && response != null) {
                profile = response
                status = LiveStatus.LOADED_SUCCESSFUL
            } else {
                status = LiveStatus.ERROR
                message = "Couldn't get profile from repository! please try again later!"
            }
        }
    }
}