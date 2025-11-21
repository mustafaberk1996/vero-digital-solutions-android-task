package com.example.verodigitalsolutionandroidtask.ui.main

import com.example.verodigitalsolutionandroidtask.domain.model.Task

data class MainUiState(
    val query: String = "",
    val data: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: MainUiError? = null,
    val logOut: Boolean = false
)

data class MainUiError(val message: String, val code: Int)