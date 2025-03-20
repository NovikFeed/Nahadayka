package com.nahadayka.model.data.remote

import com.nahadayka.model.data.remote.requestsModel.UserLoginRequest
import com.nahadayka.model.data.remote.requestsModel.UserRegisterRequest
import com.nahadayka.model.data.remote.responses.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NahadaykaAPI {
    @POST("/users/token/")
    suspend fun loginUser(
        @Header("Accept") contentType : String = "application/json",
        @Body userLoginRequest: UserLoginRequest
    ) : Response<AuthResponse>

    @POST("/users/register/")
    suspend fun registerUser(
        @Header("Accept") contentType : String = "application/json",
        @Body userRegisterRequest: UserRegisterRequest
    ) : Response<AuthResponse>
    @POST("/users/token/refresh/")
    suspend fun refreshTokens(
        @Header("Accept") contentType : String = "application/json",
        @Body refreshToken : String
    ) : Response<AuthResponse>
    
}