package com.example.mvvm

import com.example.mvvm.retrofit.GitHubApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val githubApi: GitHubApi) {
    suspend fun getUsers(since: Int, perPage: Int) = withContext(Dispatchers.IO) {
        runCatching { githubApi.getUsers(since, perPage) }
    }

    suspend fun getUserDetails(user: String) = withContext(Dispatchers.IO) {
        runCatching { githubApi.getUserDetails(user) }
    }
}