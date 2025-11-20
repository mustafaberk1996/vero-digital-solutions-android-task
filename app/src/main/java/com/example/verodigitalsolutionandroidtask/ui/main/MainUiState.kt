package com.example.verodigitalsolutionandroidtask.ui.main

import com.example.verodigitalsolutionandroidtask.domain.Task

data class MainUiState(
    val query: String = "",
    val data: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val logOut: Boolean = false
)