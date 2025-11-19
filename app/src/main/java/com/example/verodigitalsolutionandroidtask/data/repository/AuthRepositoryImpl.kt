package com.example.verodigitalsolutionandroidtask.data.repository

import com.example.verodigitalsolutionandroidtask.domain.repository.AuthRepository
import com.example.verodigitalsolutionandroidtask.network.ApiService
import com.example.verodigitalsolutionandroidtask.network.model.LoginRequest
import com.example.verodigitalsolutionandroidtask.network.model.LoginResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): AuthRepository {
    override suspend fun login(
        username: String,
        password: String
    ): LoginResponse {
        return apiService.login(LoginRequest(username, password))
    }
}