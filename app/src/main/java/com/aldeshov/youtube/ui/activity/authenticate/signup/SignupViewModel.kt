package com.aldeshov.youtube.ui.activity.authenticate.signup

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


class SignupViewModel(private val userRepository: UserRepository) : BaseComposeViewModel() {
    var email by mutableStateOf(TextFieldValue(""))
        private set

    var password by mutableStateOf(TextFieldValue(""))
        private set

    var passwordConfirm by mutableStateOf(TextFieldValue(""))
        private set

    var firstName by mutableStateOf(TextFieldValue(""))
        private set

    var lastName by mutableStateOf(TextFieldValue(""))
        private set

    var submitButtonState by mutableStateOf(SSButtonState.IDLE)
        private set

    fun signup() {
        val validMessage = dataValidate()
        if (TextUtils.isEmpty(validMessage)) {
            setStateLoading()
            userRepository.signup(
                email.text, password.text,
                firstName.text, lastName.text
            ) { response, message ->
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
                            message!!
                        )
                    }
                    else -> {
                        setState(
                            LiveStatus.ERROR,
                            SSButtonState.FAILIURE,
                            "Unknown Error"
                        )
                        Log.e("SignupViewModel", message!!)
                    }
                }
                Log.e("SignupViewModel", message!!)
            }
        }
        else {
            setState(
                LiveStatus.ERROR,
                SSButtonState.IDLE,
                validMessage
            )
        }
    }

    fun updateFirstName(firstName: TextFieldValue) {
        this.firstName = firstName
        status = LiveStatus.NOTHING
    }

    fun updateLastName(lastName: TextFieldValue) {
        this.lastName = lastName
        status = LiveStatus.NOTHING
    }

    fun updateEmail(email: TextFieldValue) {
        this.email = email
        status = LiveStatus.NOTHING
    }

    fun updatePassword(password: TextFieldValue) {
        this.password = password
        status = LiveStatus.NOTHING
    }

    fun updatePasswordConfirm(passwordConfirm: TextFieldValue) {
        this.passwordConfirm = passwordConfirm
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
        if (TextUtils.isEmpty(firstName.text))
            return "Please enter first name"
        if (TextUtils.isEmpty(lastName.text))
            return "Please enter last name"
        if (TextUtils.isEmpty(email.text))
            return "Please enter email address"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches())
            return "Please enter valid email address"
        if (TextUtils.isEmpty(password.text))
            return "Please enter password"
        if (password.text.length < 8)
            return "Password length must be more than 8"
        if (TextUtils.isEmpty(passwordConfirm.text))
            return "Please confirm the password"
        if (password.text != passwordConfirm.text)
            return "Passwords are not the same!"
        return ""
    }
}