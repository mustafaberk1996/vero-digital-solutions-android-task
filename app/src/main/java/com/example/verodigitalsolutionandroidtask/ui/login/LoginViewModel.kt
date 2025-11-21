package com.example.verodigitalsolutionandroidtask.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.verodigitalsolutionandroidtask.data.datastore.AppDataStore
import com.example.verodigitalsolutionandroidtask.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val appDataStore: AppDataStore
) : ViewModel() {

    private val _loginUiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.Idle)
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    fun checkLoginState(){
        viewModelScope.launch {
            appDataStore.accessTokenFlow.first()?.let {
                _loginUiState.value = LoginUiState.Success
            }?:run {
                _loginUiState.value = LoginUiState.Idle
            }
        }
    }

    fun loginButtonClicked(){
        viewModelScope.launch {
            _loginUiState.value = LoginUiState.Loading
            _loginUiState.value = loginUseCase()
        }
    }


}