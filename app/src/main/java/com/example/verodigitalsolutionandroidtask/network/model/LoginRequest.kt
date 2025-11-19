package com.example.verodigitalsolutionandroidtask.network.model

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val oauth: OAuth)
data class OAuth(val access_token: String)