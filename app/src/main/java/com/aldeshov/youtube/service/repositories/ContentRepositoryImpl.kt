package com.aldeshov.youtube.service.repositories

import com.aldeshov.youtube.service.models.api.content.Comment
import com.aldeshov.youtube.service.models.api.content.LikeStatus
import com.aldeshov.youtube.service.models.api.content.VideoContent
import com.aldeshov.youtube.service.network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentRepositoryImpl(
    private val api: APIService
) : ContentRepository {
    override fun getVideoContents(onResult: (isSuccess: Boolean, response: ArrayList<VideoContent>?) -> Unit) {
        api.getVideoContents()
            .enqueue(object : Callback<ArrayList<VideoContent>> {
                override fun onResponse(
                    call: Call<ArrayList<VideoContent>>,
                    response: Response<ArrayList<VideoContent>>?
                ) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ArrayList<VideoContent>>, t: Throwable) {
                    onResult(false, null)
                }
            })
    }

    override fun getVideoContent(code: String, onResult: (isSuccess: Boolean, response: VideoContent?) -> Unit) {
        api.getVideoContent(code)
            .enqueue(object : Callback<VideoContent> {
                override fun onResponse(
                    call: Call<VideoContent>,
                    response: Response<VideoContent>?
                ) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<VideoContent>, t: Throwable) {
                    onResult(false, null)
                }
            })
    }

    override fun getVideoContentComments(
        code: String,
        onResult: (isSuccess: Boolean, response: ArrayList<Comment>?) -> Unit
    ) {
        api.getVideoContentComments(code).enqueue(object : Callback<ArrayList<Comment>> {
                override fun onResponse(
                    call: Call<ArrayList<Comment>>,
                    response: Response<ArrayList<Comment>>?
                ) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<ArrayList<Comment>>, t: Throwable) {
                    onResult(false, null)
                }
            }
        )
    }

    override fun addVideoContentComment(
        code: String,
        text: String,
        onResult: (isSuccess: Boolean, response: Comment?) -> Unit
    ) {
        api.addVideoContentComment(code = code, text = text).enqueue(
            object : Callback<Comment> {
                override fun onResponse(
                    call: Call<Comment>,
                    response: Response<Comment>?
                ) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<Comment>, t: Throwable) {
                    onResult(false, null)
                }
            }
        )
    }

    override fun getLikeStatus(
        code: String,
        onResult: (isSuccess: Boolean, response: LikeStatus?) -> Unit
    ) {
        api.getVideoContentLikeStatus(code).enqueue(object : Callback<LikeStatus> {
            override fun onResponse(
                call: Call<LikeStatus>,
                response: Response<LikeStatus>?
            ) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<LikeStatus>, t: Throwable) {
                onResult(false, null)
            }
        })
    }

    override fun likeVideoContent(
        code: String,
        onResult: (isSuccess: Boolean) -> Unit
    ) {
        api.likeVideoContent(code).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful)
                    onResult(true)
                else
                    onResult(false)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onResult(false)
            }
        })
    }

    override fun dislikeVideoContent(
        code: String,
        onResult: (isSuccess: Boolean) -> Unit
    ) {
        api.dislikeVideoContent(code).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful)
                    onResult(true)
                else
                    onResult(false)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onResult(false)
            }
        })
    }

    override fun retractLikeDislikeVideoContent(
        code: String,
        onResult: (isSuccess: Boolean) -> Unit
    ) {
        api.retractLikeDislikeVideoContent(code).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful)
                    onResult(true)
                else
                    onResult(false)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onResult(false)
            }
        })
    }
}