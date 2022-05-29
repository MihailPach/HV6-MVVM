package com.example.mvvm.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.LceState
import com.example.mvvm.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class DetailsViewModel(
    private val userRepository: UserRepository,
    private val login: String
) : ViewModel() {
    val detailsFlow = flow {
        val dataFlow = userRepository.getUserDetails(login)
            .fold(
                onSuccess = { LceState.Content(it) },
                onFailure = { LceState.Error(it) }
            )
        emit(dataFlow)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )
}