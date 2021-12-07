package com.aldeshov.youtube.service.repositories

import com.aldeshov.youtube.service.database.Database
import com.aldeshov.youtube.service.database.models.LocalUser
import com.aldeshov.youtube.service.models.api.ResponseType
import com.aldeshov.youtube.service.models.api.authentication.Profile
import com.aldeshov.youtube.service.models.api.authentication.User
import com.aldeshov.youtube.service.models.api.authentication.UserLogin
import com.aldeshov.youtube.service.network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(
    private val api: APIService,
    private val db: Database
) : UserRepository {
    override fun checkCurrentUser(onResult: (response: ResponseType, message: String?) -> Unit) {
        api.getUserMe().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        // Updating User Information
                        val user = db.userDao().getUser()!!
                        user.email = response.body()!!.email
                        user.full_name = response.body()!!.full_name
                        user.avatar = response.body()!!.avatar
                        db.userDao().update(user)

                        onResult(ResponseType.RESPONSE_SUCCESSFUL, "")
                    }
                    if (response.code() in 300..399)
                        onResult(ResponseType.RESPONSE_UNSPECIFIED, response.message())
                    if (response.code() in 400..499) {
                        if (db.userDao().getUser() == null)
                            db.userDao().insert(LocalUser(id = 1))

                        // Removing User Information
                        val user = db.userDao().getUser()!!
                        user.email = null
                        user.full_name = null
                        user.avatar = null
                        user.token = null
                        db.userDao().update(user)

                        onResult(ResponseType.RESPONSE_INCORRECT_DATA, response.message())
                    }
                    if (response.code() in 500..599)
                        onResult(ResponseType.RESPONSE_SERVER_ERROR, response.message())
                } else
                    onResult(ResponseType.RESPONSE_NULL, "")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onResult(ResponseType.UNKNOWN_ERROR, t.message)
            }
        })
    }

    override fun login(
        email: String,
        password: String,
        onResult: (response: ResponseType, message: String?) -> Unit
    ) {
        api.login(email, password).enqueue(object : Callback<UserLogin> {
            override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        // Updating User Information
                        val user = db.userDao().getUser()!!
                        user.token = response.body()!!.token
                        db.userDao().update(user)
                        onResult(ResponseType.RESPONSE_SUCCESSFUL, "")
                    }
                    if (response.code() in 300..399)
                        onResult(ResponseType.RESPONSE_UNSPECIFIED, response.message())
                    if (response.code() in 400..499)
                        onResult(ResponseType.RESPONSE_INCORRECT_DATA, response.message())
                    if (response.code() in 500..599)
                        onResult(ResponseType.RESPONSE_SERVER_ERROR, response.message())
                } else
                    onResult(ResponseType.RESPONSE_NULL, "")
            }

            override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                onResult(ResponseType.UNKNOWN_ERROR, t.message)
            }
        })
    }

    override fun logout(onResult: (isSuccess: Boolean) -> Unit) {
        if (db.userDao().getUser() == null)
            onResult(false)

        db.userDao().getUser()?.let {
            db.userDao().delete(it)
            onResult(true)
        }
    }

    override fun signup(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        onResult: (response: ResponseType, message: String?) -> Unit
    ) {
        api.signup(email, password, firstName, lastName).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>?) {
                if (response != null) {
                    if (response.isSuccessful)
                        onResult(ResponseType.RESPONSE_SUCCESSFUL, "")
                    if (response.code() in 300..399)
                        onResult(ResponseType.RESPONSE_UNSPECIFIED, response.message())
                    if (response.code() in 400..499)
                        onResult(ResponseType.RESPONSE_INCORRECT_DATA, response.message())
                    if (response.code() in 500..599)
                        onResult(ResponseType.RESPONSE_SERVER_ERROR, response.message())
                } else
                    onResult(ResponseType.RESPONSE_NULL, "")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onResult(ResponseType.UNKNOWN_ERROR, t.message)
            }
        })
    }

    override fun myProfile(onResult: (isSuccess: Boolean, profile: Profile?) -> Unit) {
        api.getMyProfile().enqueue(object : Callback<Profile> {
            override fun onResponse(call: Call<Profile>, response: Response<Profile>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                onResult(false, null)
            }
        })
    }

    override fun localUser(onResult: (user: LocalUser?) -> Unit) {
        onResult(db.userDao().getUser())
    }
}