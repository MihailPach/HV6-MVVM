package com.example.mvvm.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object GitHubService {
    private val retrofit by lazy(LazyThreadSafetyMode.NONE) { provideRetrofit() }
    val githubApi by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create<GitHubApi>()
    }

    private fun provideRetrofit(): Retrofit {
        OkHttpClient.Builder()
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.gitHub.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}