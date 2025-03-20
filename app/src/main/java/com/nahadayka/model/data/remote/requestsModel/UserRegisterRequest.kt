package com.nahadayka.model.data.remote.requestsModel

data class UserRegisterRequest(
    val email : String,
    val password : String,
    val password2 : String
) {
}