package com.example.mvvm.koin

import androidx.room.Room
import com.example.mvvm.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room
            .databaseBuilder(
                get(),
                AppDatabase::class.java,
                "mvvm_database.db"
            )
            .build()
    }
}