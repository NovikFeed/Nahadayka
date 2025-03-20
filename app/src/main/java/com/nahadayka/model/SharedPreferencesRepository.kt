package com.nahadayka.model

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesRepository @Inject constructor(
    @ApplicationContext private val context : Context
) {
    private val sharedPreferences = context.getSharedPreferences("nahadayka", Context.MODE_PRIVATE)


    fun getString(key : String): String {
        val data = sharedPreferences.getString(key, "")
        data?.let{
            return it
        }
        return ""
    }

    fun saveString(value : String?, key : String){
        sharedPreferences.edit() { putString(key, value) }
    }

    fun removeString(key : String){
        sharedPreferences.edit() { remove(key) }
    }








}