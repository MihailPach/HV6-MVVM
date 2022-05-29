package com.example.mvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GithubUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "avatarUrl")
    @SerializedName("avatar_url")
    val avatarUrl: String
)