package com.aldeshov.youtube.ui.activity.main.account

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.aldeshov.youtube.service.database.models.LocalUser
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.service.repositories.UserRepository
import com.aldeshov.youtube.ui.activity.main.MainActivity
import org.koin.core.annotation.KoinInternalApi

class AccountViewModel(private val userRepository: UserRepository) : BaseComposeViewModel() {
    var user by mutableStateOf(LocalUser())

    fun getLocalUser() {
        userRepository.localUser {
            if (it != null) {
                user = it
                status = LiveStatus.LOADED_SUCCESSFUL
            } else {
                status = LiveStatus.ERROR
                message = "Error occurred when getting local user"
            }
        }
    }

    @KoinInternalApi
    fun logout(context: Context) {
        userRepository.logout {
            if (!it) {
                Toast.makeText(context, "Error occurred when logging out", Toast.LENGTH_LONG)
                    .show()
            }
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as ComponentActivity).finish()
        }
    }
}