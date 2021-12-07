package com.aldeshov.youtube.ui.activity.authenticate.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.models.LiveStatus
import com.aldeshov.youtube.ui.activity.main.MainActivity
import com.aldeshov.youtube.ui.templates.ScrollableFormBox
import com.aldeshov.youtube.ui.templates.*
import org.koin.core.annotation.KoinInternalApi

@Composable
fun SignupWidget(signupAction: () -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                color = Color.Black,
                text = stringResource(id = R.string.you_do_not_have_account_text),
                fontSize = 15.sp
            )
            TextButton(onClick = signupAction, contentPadding = PaddingValues(4.dp)) {
                Text(
                    text = stringResource(id = R.string.create_one_text),
                    color = MaterialTheme.colors.onSurface
                )
            }
        }

        TermsTextWidget()
    }
}

@KoinInternalApi
@Composable
fun LoginApp(loginViewModel: LoginViewModel, signupAction: () -> Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    if (loginViewModel.status == LiveStatus.LOADED_SUCCESSFUL) {
        context.startActivity(Intent(context, MainActivity::class.java))
        (context as AppCompatActivity).finish()
    }

    ScrollableFormBox {
        BrandHeaderWidget()

        EmailInput(
            status = loginViewModel.status,
            focusManager = focusManager,
            email = loginViewModel.email,
            updateEmail = loginViewModel::updateEmail
        )

        PasswordInput(
            status = loginViewModel.status,
            focusManager = focusManager,
            password = loginViewModel.password,
            updatePassword = loginViewModel::updatePassword,
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
            if (loginViewModel.status == LiveStatus.ERROR)
                ErrorWidget(message = loginViewModel.message)
        }

        SubmitButton(
            text = stringResource(id = R.string.log_in),
            enabled = loginViewModel.status != LiveStatus.LOADING,
            submitSSButtonState = loginViewModel.submitButtonState,
            submitAction = loginViewModel::login
        )

        SignupWidget(signupAction = signupAction)
    }
}