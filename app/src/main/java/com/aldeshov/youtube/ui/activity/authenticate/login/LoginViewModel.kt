package com.aldeshov.youtube.ui.activity.authenticate.login

import android.text.TextUtils
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.service.models.api.ResponseType
import com.aldeshov.youtube.service.models.base.BaseComposeViewModel
import com.aldeshov.youtube.service.repositories.UserRepository
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonState

class LoginViewModel(private val userRepository: UserRepository) : BaseComposeViewModel() {
    var email by mutableStateOf(TextFieldValue(""))
        private set

    var password by mutableStateOf(TextFieldValue(""))
        private set

    var submitButtonState by mutableStateOf(SSButtonState.IDLE)
        private set

    fun login() {
        val validMessage = dataValidate()
        if (TextUtils.isEmpty(validMessage)) {
            setStateLoading()
            userRepository.login(email.text, password.text) { response, message ->
                when (response) {
                    ResponseType.RESPONSE_SUCCESSFUL -> {
                        setState(
                            LiveStatus.LOADED_SUCCESSFUL,
                            SSButtonState.SUCCESS,
                            ""
                        )
                    }
                    ResponseType.RESPONSE_INCORRECT_DATA -> {
                        setState(
                            LiveStatus.ERROR,
                            SSButtonState.FAILIURE,
                            "Email or password is incorrect!"
                        )
                    }
                    else -> {
                        setState(
                            LiveStatus.ERROR,
                            SSButtonState.FAILIURE,
                            "Unknown Error"
                        )
                        Log.e("LoginViewModel", message!!)
                    }
                }
            }
        } else {
            setState(
                LiveStatus.ERROR,
                SSButtonState.IDLE,
                validMessage
            )
        }
    }

    fun updateEmail(email: TextFieldValue) {
        this.email = email
        status = LiveStatus.NOTHING
    }

    fun updatePassword(password: TextFieldValue) {
        this.password = password
        status = LiveStatus.NOTHING
    }

    private fun setState(
        status: LiveStatus,
        submitButtonState: SSButtonState,
        message: String
    ) {
        this.status = status
        this.submitButtonState = submitButtonState
        this.message = message
    }

    private fun setStateLoading() {
        this.status = LiveStatus.LOADING
        this.submitButtonState = SSButtonState.LOADING
        this.message = ""
    }

    private fun dataValidate(): String {
        if (TextUtils.isEmpty(email.text))
            return "Please enter email address"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches())
            return "Please enter valid email address"
        if (TextUtils.isEmpty(password.text))
            return "Please enter password"

        return ""
    }
}