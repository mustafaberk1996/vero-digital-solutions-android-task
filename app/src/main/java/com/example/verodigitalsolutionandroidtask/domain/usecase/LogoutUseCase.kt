package com.example.verodigitalsolutionandroidtask.domain.usecase

import com.example.verodigitalsolutionandroidtask.data.datastore.AuthDataStore
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authDataStore: AuthDataStore
){
    suspend operator fun invoke(){
        authDataStore.removeAccessToken()
    }
}