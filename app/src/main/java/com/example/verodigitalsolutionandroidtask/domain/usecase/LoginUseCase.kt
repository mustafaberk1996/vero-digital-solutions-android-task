package com.example.verodigitalsolutionandroidtask.domain.usecase

import com.example.verodigitalsolutionandroidtask.data.datastore.AppDataStore
import com.example.verodigitalsolutionandroidtask.domain.repository.AuthRepository
import com.example.verodigitalsolutionandroidtask.ui.login.LoginUiState
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val authRepository: AuthRepository, private val appDataStore: AppDataStore) {

    suspend operator fun invoke(username: String, password: String): LoginUiState {
        try {
            val result =  authRepository.login(username, password)
            appDataStore.saveAccessToken(result.oauth.access_token)
            return LoginUiState.Success
        }catch (exception: Exception){
            return LoginUiState.Error(exception)
        }
    }
}

