package com.example.mvvm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvm.model.GithubUser

@Database(entities = [GithubUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}