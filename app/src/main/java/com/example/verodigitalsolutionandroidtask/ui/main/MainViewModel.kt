package com.example.verodigitalsolutionandroidtask.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verodigitalsolutionandroidtask.domain.Task
import com.example.verodigitalsolutionandroidtask.domain.filter
import com.example.verodigitalsolutionandroidtask.domain.usecase.FetchTasks
import com.example.verodigitalsolutionandroidtask.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTasks: FetchTasks,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {


    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Idle)
    val uiState: MutableStateFlow<MainUiState> = _uiState

    val filteredTasks: StateFlow<List<Task>> = combine(
        _query,
        _uiState
    ) { query, state ->
        if (state is MainUiState.TaskList) {
            if (query.isEmpty()) {
                state.taskList
            } else {
                state.taskList.filter {
                 it.filter(query)
                }
            }
        } else emptyList()
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )


    init {
        viewModelScope.launch {
             _uiState.value = MainUiState.Loading
             _uiState.value = fetchTasks()
        }
    }

    fun onQueryChanged(query: String){
        _query.value = query
        Timber.d("query changed: $query")
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.value = MainUiState.Logout
        }
    }

}