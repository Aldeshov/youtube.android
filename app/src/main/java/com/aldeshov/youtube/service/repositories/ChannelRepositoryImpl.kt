package com.aldeshov.youtube.service.repositories

import com.aldeshov.youtube.service.models.api.applications.Channel
import com.aldeshov.youtube.service.models.api.applications.SubscribeStatus
import com.aldeshov.youtube.service.network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChannelRepositoryImpl(private val api: APIService): ChannelRepository {
    override fun getSubscribeStatus(
        code: String,
        onResult: (isSuccess: Boolean, response: SubscribeStatus?) -> Unit
    ) {
        api.getChannelSubscribeStatus(code).enqueue(
            object : Callback<SubscribeStatus> {
                override fun onResponse(
                    call: Call<SubscribeStatus>,
                    response: Response<SubscribeStatus>?
                ) {
                    if (response != null && response.isSuccessful)
                        onResult(true, response.body()!!)
                    else
                        onResult(false, null)
                }

                override fun onFailure(call: Call<SubscribeStatus>, t: Throwable) {
                    onResult(false, null)
                }
            }
        )
    }

    override fun subscribe(code: String, onResult: (isSuccess: Boolean) -> Unit) {
        api.subscribeChannel(code).enqueue(object : Callback<Unit> {
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

    override fun unsubscribe(code: String, onResult: (isSuccess: Boolean) -> Unit) {
        api.unsubscribeChannel(code).enqueue(object : Callback<Unit> {
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

    override fun myChannel(onResult: (isSuccess: Boolean, response: Channel?) -> Unit) {
        api.getMyChannel().enqueue(object : Callback<Channel> {
            override fun onResponse(call: Call<Channel>, response: Response<Channel>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else {
                    if (response != null && response.code() == 400)
                        onResult(true, null)
                    else
                        onResult(false, null)
                }
            }

            override fun onFailure(call: Call<Channel>, t: Throwable) {
                onResult(false, null)
            }
        })
    }
}