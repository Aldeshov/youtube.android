package com.aldeshov.youtube.ui.activity.authenticate.signup

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.ui.templates.ScrollableFormBox
import com.aldeshov.youtube.ui.templates.*

@Composable
fun SignupApp(signupViewModel: SignupViewModel, signedUpAction: () -> Unit) {
    val focusManager = LocalFocusManager.current

    if (signupViewModel.status == LiveStatus.LOADED_SUCCESSFUL)
        signedUpAction()

    ScrollableFormBox {
        BrandHeaderWidget(subText = stringResource(id = R.string.create_new_account_now_text))

        TextInput(
            status = signupViewModel.status,
            focusManager = focusManager,
            label = stringResource(id = R.string.first_name),
            value = signupViewModel.firstName,
            updateValue = signupViewModel::updateFirstName
        )

        TextInput(
            status = signupViewModel.status,
            focusManager = focusManager,
            label = stringResource(id = R.string.last_name),
            value = signupViewModel.lastName,
            updateValue = signupViewModel::updateLastName
        )

        EmailInput(
            status = signupViewModel.status,
            focusManager = focusManager,
            email = signupViewModel.email,
            updateEmail = signupViewModel::updateEmail
        )

        PasswordInput(
            status = signupViewModel.status,
            focusManager = focusManager,
            password = signupViewModel.password,
            updatePassword = signupViewModel::updatePassword
        )

        PasswordInput(
            status = signupViewModel.status,
            focusManager = focusManager,
            password = signupViewModel.passwordConfirm,
            updatePassword = signupViewModel::updatePasswordConfirm,
            isPasswordConfirmation = true,
            isLastInput = true
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            if (signupViewModel.status == LiveStatus.ERROR)
                ErrorWidget(message = signupViewModel.message)
        }

        SubmitButton(
            text = stringResource(id = R.string.sign_up),
            enabled = signupViewModel.status != LiveStatus.LOADING,
            submitSSButtonState = signupViewModel.submitButtonState,
            submitAction = signupViewModel::signup
        )

        TermsTextWidget()
    }
}