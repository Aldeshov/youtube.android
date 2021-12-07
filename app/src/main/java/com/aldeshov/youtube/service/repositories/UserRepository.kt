package com.aldeshov.youtube.service.repositories

import com.aldeshov.youtube.service.database.models.LocalUser
import com.aldeshov.youtube.service.models.api.ResponseType
import com.aldeshov.youtube.service.models.api.authentication.Profile


interface UserRepository {
    fun checkCurrentUser(
        onResult: (response: ResponseType, message: String?) -> Unit
    )

    fun login(
        email: String,
        password: String,
        onResult: (response: ResponseType, message: String?) -> Unit
    )

    fun logout(onResult: (isSuccess: Boolean) -> Unit)

    fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        onResult: (response: ResponseType, message: String?) -> Unit
    )

    fun myProfile(onResult: (isSuccess: Boolean, profile: Profile?) -> Unit)

    fun localUser(onResult: (user: LocalUser?) -> Unit)
}