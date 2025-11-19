package com.example.verodigitalsolutionandroidtask.data.repository

import com.example.verodigitalsolutionandroidtask.data.mapToDomain
import com.example.verodigitalsolutionandroidtask.domain.Task
import com.example.verodigitalsolutionandroidtask.domain.repository.TaskRepository
import com.example.verodigitalsolutionandroidtask.network.ApiService
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): TaskRepository {
    override suspend fun getTasks(): List<Task> {
       return apiService.getTasks().map { it.mapToDomain() }
    }
}