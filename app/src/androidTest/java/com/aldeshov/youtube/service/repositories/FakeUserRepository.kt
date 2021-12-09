package com.aldeshov.youtube.service.repositories

import com.aldeshov.youtube.service.database.Database
import com.aldeshov.youtube.service.database.UserDao
import com.aldeshov.youtube.service.database.models.LocalUser
import com.aldeshov.youtube.service.models.api.ResponseType
import com.aldeshov.youtube.service.models.api.authentication.Profile
import com.aldeshov.youtube.service.network.APIService
import com.aldeshov.youtube.service.repositories.UserRepository

class FakeUserRepository(private val db: Database) : UserRepository {
    var localUser: LocalUser = LocalUser()

    override fun checkCurrentUser(onResult: (response: ResponseType, message: String?) -> Unit) {
        localUser = db.userDao().getUser()!!
    }

    override fun login(
        email: String,
        password: String,
        onResult: (response: ResponseType, message: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun logout(onResult: (isSuccess: Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        onResult: (response: ResponseType, message: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun myProfile(onResult: (isSuccess: Boolean, profile: Profile?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun localUser(onResult: (user: LocalUser?) -> Unit) {
        TODO("Not yet implemented")
    }
}