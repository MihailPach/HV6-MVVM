package com.example.mvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GithubUserDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "avatarUrl")
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "followers")
    val followers: Int,
    @ColumnInfo(name = "following")
    val following: Int
)