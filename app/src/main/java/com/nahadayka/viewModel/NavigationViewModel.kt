package com.nahadayka.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nahadayka.model.SharedPreferencesRepository
import com.nahadayka.model.repositories.LanguageRepository
import com.nahadayka.model.repositories.NahadaykaRepository
import com.nahadayka.view.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferencesRepository,
    private val repository: NahadaykaRepository,
    private val languageRepository: LanguageRepository
) : ViewModel() {
    private val _navigationState = MutableStateFlow(NavigationState())
    val navigationState = _navigationState.asStateFlow()

    init {
        checkAuth()
    }

    fun startAuth(email : String, password : String, confirmPassword : String = "", isLogin : Boolean){
        viewModelScope.launch {
            if(isLogin){
                _navigationState.value = NavigationState(
                    isLoading = true,
                    viewMessage = false
                )
                val response = repository.loginUser(email, password)
                if (response.isSuccess){
                    sharedPreferences.saveString(response.responseBody?.access, "access")
                    sharedPreferences.saveString(response.responseBody?.refresh, "refresh")
                    sharedPreferences.saveString((System.currentTimeMillis()+60*60*1000).toString(), "expiration")
                    _navigationState.value = NavigationState(
                        isLoading = false,
                        message = "ok",
                        screenRoute = Screen.Home.route
                    )
                }
                else{
                    _navigationState.value = NavigationState(
                        isLoading = false,
                        message = response.message,
                        viewMessage = true
                    )
                }
            }
            else{
                _navigationState.value = NavigationState(
                    isLoading = true,
                    viewMessage = false,
                    isLogin = false
                )
                val response = repository.registerUser(email, password, confirmPassword)
                if(response.isSuccess){
                    _navigationState.value = NavigationState(
                        isLoading = false,
                        message = "ok",
                        screenRoute = Screen.Login.route
                    )
                }
                else{
                    _navigationState.value = NavigationState(
                        isLoading = false,
                        message = response.message,
                        viewMessage = true,
                        isLogin = false
                    )
                }
            }
        }

    }


    private fun checkAuth() {
        val access = sharedPreferences.getString("access")
        val refresh = sharedPreferences.getString("refresh")
        val expirationTime = sharedPreferences.getString("expiration")
        if (expirationTime != "") {
            if (expirationTime.toLong() > System.currentTimeMillis()) {
                if(access != "" && refresh != ""){
                    _navigationState.value = NavigationState(
                        isLoading = false,
                        message = "ok",
                        screenRoute = Screen.Home.route
                    )
                }
                else{
                    _navigationState.value = NavigationState(
                        isLoading = false,
                        message = "ok",
                        screenRoute = Screen.Login.route
                    )
                }
            } else {
                viewModelScope.launch {
                    _navigationState.value =
                        NavigationState(isLoading = true, message = "Access token expired")
                    val response = repository.refreshTokens(refresh)
                    if (response.isSuccess) {
                        sharedPreferences.saveString(response.responseBody?.access, "access")
                        sharedPreferences.saveString(response.responseBody?.refresh, "refresh")
                        sharedPreferences.saveString((System.currentTimeMillis()+60*60*1000).toString(), "expiration")
                        _navigationState.value = NavigationState(
                            isLoading = false,
                            message = "ok",
                            screenRoute = Screen.Home.route
                        )
                    } else {
                        _navigationState.value = NavigationState(
                            isLoading = false,
                            message = response.message,
                            viewMessage = true
                        )
                    }
                }
            }
        } else {
            _navigationState.value = NavigationState()
        }
    }
    fun setLanguage(language : String): Context{
        val context = languageRepository.setLanguage(language)
        languageRepository.updateAppLocale()
        return context
    }
}