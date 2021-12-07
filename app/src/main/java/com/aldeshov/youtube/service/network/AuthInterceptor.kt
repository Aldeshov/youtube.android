package com.aldeshov.youtube.service.network

import android.text.TextUtils
import com.aldeshov.youtube.service.database.Database
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val db: Database) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = db.userDao().getUser()?.token

        if (TextUtils.isEmpty(token)) {
            val newRequest: Request = chain.request().newBuilder().build()
            return chain.proceed(newRequest)
        }

        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "JWT $token")
            .build()
        return chain.proceed(newRequest)
    }
}