package com.aldeshov.youtube.service.network

import com.aldeshov.youtube.service.models.api.applications.Channel
import com.aldeshov.youtube.service.models.api.applications.SubscribeStatus
import com.aldeshov.youtube.service.models.api.authentication.Profile
import com.aldeshov.youtube.service.models.api.content.VideoContent
import com.aldeshov.youtube.service.models.api.authentication.UserLogin
import com.aldeshov.youtube.service.models.api.authentication.User
import com.aldeshov.youtube.service.models.api.content.Comment
import com.aldeshov.youtube.service.models.api.content.LikeStatus
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @GET("app/contents")
    fun getVideoContents(): Call<ArrayList<VideoContent>>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @GET("app/contents/{code}")
    fun getVideoContent(@Path("code") code: String): Call<VideoContent>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @GET("app/contents/{code}/comments")
    fun getVideoContentComments(@Path("code") code: String): Call<ArrayList<Comment>>

    @FormUrlEncoded
    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @POST("app/contents/{code}/comments")
    fun addVideoContentComment(
        @Path("code") code: String,
        @Field("text") text: String
    ): Call<Comment>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @GET("app/contents/{code}/like/status")
    fun getVideoContentLikeStatus(@Path("code") code: String): Call<LikeStatus>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @PUT("app/contents/{code}/like?dislike=0&retract=0")
    fun likeVideoContent(@Path("code") code: String): Call<Unit>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @PUT("app/contents/{code}/like?dislike=1&retract=0")
    fun dislikeVideoContent(@Path("code") code: String): Call<Unit>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @PUT("app/contents/{code}/like?dislike=0&retract=1")
    fun retractLikeDislikeVideoContent(@Path("code") code: String): Call<Unit>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @GET("app/channels/{code}/subscribe/status")
    fun getChannelSubscribeStatus(@Path("code") code: String): Call<SubscribeStatus>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @PUT("app/channels/{code}/subscribe?undo=0")
    fun subscribeChannel(@Path("code") code: String): Call<Unit>

    @Headers("Cache-Control: max-age=640000", "User-Agent: YouTube")
    @PUT("app/channels/{code}/subscribe?undo=1")
    fun unsubscribeChannel(@Path("code") code: String): Call<Unit>

    @GET("auth/users/me")
    fun getUserMe(): Call<User>

    @GET("auth/users/me/profile")
    fun getMyProfile(): Call<Profile>

    @GET("app/channels/me")
    fun getMyChannel(): Call<Channel>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserLogin>

    @FormUrlEncoded
    @POST("auth/register")
    fun signup(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
    ): Call<User>
}
