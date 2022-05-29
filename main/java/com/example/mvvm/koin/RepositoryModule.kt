package com.example.mvvm.koin

import com.example.mvvm.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get()) }
}