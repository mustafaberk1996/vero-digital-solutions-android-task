package com.example.verodigitalsolutionandroidtask.domain.usecase

import com.example.verodigitalsolutionandroidtask.data.datastore.AppDataStore
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val appDataStore: AppDataStore
){
    suspend operator fun invoke(){
        appDataStore.clearAllData()
    }
}