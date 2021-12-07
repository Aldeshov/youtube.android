package com.aldeshov.youtube.ui.templates

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.ContactPage
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.aldeshov.youtube.R
import com.aldeshov.youtube.service.models.LiveStatus
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonState
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonType
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSJetPackComposeProgressButton

@Composable
fun TextInput(
    status: LiveStatus,
    focusManager: FocusManager,
    label: String,
    value: TextFieldValue,
    updateValue: (value: TextFieldValue) -> Unit,
    isLastInput: Boolean = false
) {
    Card(
        elevation = 2.dp,
        shape = AbsoluteRoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = value,
            singleLine = true,
            onValueChange = updateValue,
            label = { Text(text = label) },
            isError = status == LiveStatus.ERROR,
            enabled = status != LiveStatus.LOADING,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = if (isLastInput) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = getKeyboardActions(isLastInput, focusManager),
            leadingIcon = {
                Icon(
                    Icons.Outlined.ContactPage,
                    contentDescription = label,
                )
            }
        )
    }
}

@Composable
fun EmailInput(
    status: LiveStatus,
    focusManager: FocusManager,
    email: TextFieldValue,
    updateEmail: (email: TextFieldValue) -> Unit,
    isLastInput: Boolean = false
) {
    Card(
        elevation = 2.dp,
        shape = AbsoluteRoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = email,
            singleLine = true,
            onValueChange = updateEmail,
            label = { Text(text = stringResource(id = R.string.email)) },
            isError = status == LiveStatus.ERROR,
            enabled = status != LiveStatus.LOADING,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = if (isLastInput) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = getKeyboardActions(isLastInput, focusManager),
            leadingIcon = {
                Icon(
                    Icons.Outlined.Email,
                    contentDescription = stringResource(id = R.string.email),
                )
            }
        )
    }
}

@Composable
fun PasswordInput(
    status: LiveStatus,
    focusManager: FocusManager,
    password: TextFieldValue,
    updatePassword: (email: TextFieldValue) -> Unit,
    isLastInput: Boolean = false,
    isPasswordConfirmation: Boolean = false,
    passwordVisibility: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    Card(
        elevation = 2.dp,
        shape = AbsoluteRoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = password,
            singleLine = true,
            onValueChange = updatePassword,
            isError = status == LiveStatus.ERROR,
            enabled = status != LiveStatus.LOADING,
            visualTransformation =
            if (passwordVisibility.value)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = if (isLastInput) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = getKeyboardActions(isLastInput, focusManager),
            label = {
                Text(
                    text =
                    if (isPasswordConfirmation)
                        stringResource(id = R.string.confirm_password_text)
                    else
                        stringResource(id = R.string.password),
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Lock,
                    contentDescription =
                    if (isPasswordConfirmation)
                        stringResource(id = R.string.confirm_password_text)
                    else
                        stringResource(id = R.string.password),
                )
            },
            trailingIcon = {
                if (!isPasswordConfirmation) {
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(
                            imageVector =
                            if (passwordVisibility.value)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = stringResource(id = R.string.visibility),
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun SubmitButton(
    text: String,
    enabled: Boolean,
    submitSSButtonState: SSButtonState,
    submitAction: () -> Unit
) {
    SSJetPackComposeProgressButton(
        text = text,
        height = 50.dp,
        width = 256.dp,
        speedMillis = 200,
        enabled = enabled,
        type = SSButtonType.CIRCLE,
        padding = PaddingValues(16.dp),
        onClick = submitAction,
        fontWeight = FontWeight.Bold,
        buttonState = submitSSButtonState,
        assetColor = Color.White
    )
}

private fun getKeyboardActions(isLastInput: Boolean, focusManager: FocusManager): KeyboardActions {
    if (isLastInput)
        return KeyboardActions(onDone = { focusManager.clearFocus() })
    return KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
}