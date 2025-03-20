package com.nahadayka.model.repositories

import com.nahadayka.model.data.remote.NahadaykaAPI
import com.nahadayka.model.data.remote.ResponseState
import com.nahadayka.model.data.remote.requestsModel.UserLoginRequest
import com.nahadayka.model.data.remote.requestsModel.UserRegisterRequest
import com.nahadayka.model.data.remote.responses.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.Response
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
    suspend fun loginUser(email : String, password: String) : ResponseState<AuthResponse>{
        return withContext(Dispatchers.IO) {
            try {
                val response =
                    nahadaykaAPI.loginUser(userLoginRequest = UserLoginRequest(email, password))
                if (response.isSuccessful) {
                    ResponseState<AuthResponse>(isSuccess = true, responseBody = response.body())
                } else {
                    ResponseState<AuthResponse>(isSuccess = false, message = response.message())
                }
            }
            catch (e : Exception){
                ResponseState<AuthResponse>(isSuccess = false, message = e.message.toString())
            }
        }
    }

    suspend fun registerUser(email: kotlin.String, password: String, confirmPassword: String): ResponseState<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = nahadaykaAPI.registerUser(userRegisterRequest = UserRegisterRequest(email, password, confirmPassword))
                if(response.isSuccessful){
                    ResponseState<String>(isSuccess = true)
                }
                else{
                    ResponseState<String>(isSuccess = true, responseBody = response.message())
                }
            }
            catch (e : Exception){
                ResponseState<String>(isSuccess = false, message = e.message.toString())
            }
        }
    }
}