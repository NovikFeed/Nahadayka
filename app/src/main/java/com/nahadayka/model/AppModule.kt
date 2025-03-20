package com.nahadayka.model

import com.nahadayka.model.data.remote.NahadaykaAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesNahadaykaAPI(): NahadaykaAPI {
       return Retrofit.Builder()
           .baseUrl("https://core-x4td.onrender.com")
           .client(client)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(NahadaykaAPI::class.java)

    }
}