package com.example.verodigitalsolutionandroidtask.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verodigitalsolutionandroidtask.domain.usecase.FetchTasks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTasks: FetchTasks
): ViewModel() {



    private val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Idle)
    val uiState: MutableStateFlow<MainUiState> = _uiState

    init {
        viewModelScope.launch {
             _uiState.value = MainUiState.Loading
             _uiState.value = fetchTasks()
        }
    }
}