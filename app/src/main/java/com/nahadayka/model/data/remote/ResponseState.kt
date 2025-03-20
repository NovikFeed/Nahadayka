package com.nahadayka.model.data.remote

import retrofit2.Response

data class ResponseState<T>(
    val isSuccess : Boolean = true,
    val message : String = "",
    val responseBody : T? = null
) {
}