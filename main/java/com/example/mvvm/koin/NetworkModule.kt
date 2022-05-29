package com.example.mvvm.koin

import com.example.mvvm.retrofit.GitHubApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val networkModule = module {
    single {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.gitHub.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

    }
    single {
        get<Retrofit>().create<GitHubApi>()
    }
}