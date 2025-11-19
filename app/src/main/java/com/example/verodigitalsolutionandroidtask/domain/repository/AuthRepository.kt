package com.example.verodigitalsolutionandroidtask.domain.repository

import com.example.verodigitalsolutionandroidtask.network.model.LoginResponse

interface AuthRepository {

    suspend fun login(username: String, password: String): LoginResponse

}