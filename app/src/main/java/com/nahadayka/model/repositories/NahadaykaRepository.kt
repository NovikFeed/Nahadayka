package com.nahadayka.model.repositories

import com.nahadayka.model.data.remote.NahadaykaAPI
import com.nahadayka.model.data.remote.ResponseState
import com.nahadayka.model.data.remote.responses.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class NahadaykaRepository @Inject constructor(
    private val nahadaykaAPI: NahadaykaAPI
) {
    suspend fun refreshTokens(refreshToken: String): ResponseState<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = nahadaykaAPI.refreshTokens(refreshToken = refreshToken)
                if (response.isSuccessful) {
                    ResponseState<AuthResponse>(isSuccess = true, responseBody = response.body())
                }
                else{
                    ResponseState<AuthResponse>(isSuccess = false, message = response.message())
                }
            } catch (e: Exception) {
                ResponseState<AuthResponse>(false, e.message?.toString() ?: "Error")
            }
        }
    }
}