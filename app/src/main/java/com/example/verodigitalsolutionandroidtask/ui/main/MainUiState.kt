package com.example.verodigitalsolutionandroidtask.ui.main

import com.example.verodigitalsolutionandroidtask.domain.Task

sealed class MainUiState {
    object Loading: MainUiState()
    class TaskList(val taskList:List<Task>): MainUiState()
    class Error(val throwable: Throwable?): MainUiState()
    object Logout: MainUiState()
    object Idle: MainUiState()
}