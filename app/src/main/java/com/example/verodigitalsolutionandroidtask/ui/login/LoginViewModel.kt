package com.example.verodigitalsolutionandroidtask.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verodigitalsolutionandroidtask.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val username = "365"
    private val password = "1"


    private val _loginUiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()


    fun loginButtonClicked(){
        viewModelScope.launch {
            _loginUiState.value = loginUseCase(username, password)
        }
    }


}