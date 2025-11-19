package com.example.verodigitalsolutionandroidtask.ui.login

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    class Error(val exception: Throwable) : LoginUiState()
}