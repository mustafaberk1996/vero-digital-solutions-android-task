package com.example.verodigitalsolutionandroidtask.network

import com.example.verodigitalsolutionandroidtask.data.network.model.TaskResponse
import com.example.verodigitalsolutionandroidtask.network.model.LoginRequest
import com.example.verodigitalsolutionandroidtask.network.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    @Headers( "Authorization: Basic QVBJX0V4cGxvcmVyOjEyMzQ1NmlzQUxhbWVQYXNz", "Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("v1/tasks/select")
    suspend fun getTasks():List<TaskResponse>


}