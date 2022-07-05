package com.example.caronews.repository

import com.example.caronews.model.NewsListResponse
import com.example.caronews.network.NetworkCall
import retrofit2.Retrofit
import javax.inject.Inject

class MainReository @Inject constructor(private val retrofitInstance: Retrofit) {

    suspend fun getData(): ResponseState {
        val responseData = retrofitInstance.create(NetworkCall::class.java).getQuotes()
        if (responseData.isSuccessful)
            responseData.body()?.let {
                return ResponseState.SuccessData(it)
            } ?: kotlin.run {
                return ResponseState.FailData
            }
        else
            return ResponseState.FailData

    }

}


sealed class ResponseState {
    data class SuccessData(val listOfNews: List<NewsListResponse>) : ResponseState()
    object FailData : ResponseState()
}