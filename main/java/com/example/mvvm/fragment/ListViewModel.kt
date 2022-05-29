package com.example.mvvm.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.UserRepository
import com.example.mvvm.database.UserDao
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class ListViewModel(
    private val userRepository: UserRepository,
    private val userDao: UserDao
) : ViewModel() {

    private var isLoading = false
    private var currentPage = 0

    private val loadMoreFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val dataFlow = loadMoreFlow
        .filter { !isLoading }
        .onEach {
            isLoading = true
        }
        .map {
            userRepository.getUsers(currentPage * PAGE_SIZE, PAGE_SIZE)
                .apply { isLoading = false }
                .fold(
                    onSuccess = {
                        userDao.insertUsers(it)
                        currentPage++
                        it
                    },
                    onFailure = {
                        emptyList()
                    }
                )
        }
        .runningReduce { accumulator, value -> accumulator + value }
        .onStart {
            emit(userDao.getUsers())
        }.shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1
        )

    fun onLoadMore() {
        loadMoreFlow.tryEmit(Unit)
    }

    companion object {
        private const val PAGE_SIZE = 50
    }
}