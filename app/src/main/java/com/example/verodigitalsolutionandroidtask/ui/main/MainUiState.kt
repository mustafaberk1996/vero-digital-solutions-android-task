package com.example.verodigitalsolutionandroidtask.ui.main

import com.example.verodigitalsolutionandroidtask.domain.Task

sealed class MainUiState {
    object Loading: MainUiState()
    class TaskList(val taskList:List<Task>): MainUiState()
    object Error: MainUiState()
    object Empty: MainUiState()
    object Idle: MainUiState()
}