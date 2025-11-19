package com.example.verodigitalsolutionandroidtask.data.repository

import com.example.verodigitalsolutionandroidtask.data.local.dao.TaskDao
import com.example.verodigitalsolutionandroidtask.data.local.entity.mapToDomain
import com.example.verodigitalsolutionandroidtask.data.mapToEntity
import com.example.verodigitalsolutionandroidtask.domain.Task
import com.example.verodigitalsolutionandroidtask.domain.repository.TaskRepository
import com.example.verodigitalsolutionandroidtask.network.ApiService
import com.example.verodigitalsolutionandroidtask.ui.network.NetworkManager
import javax.inject.Inject
import kotlin.collections.map

class TaskRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val taskDao: TaskDao,
    private val networkManager: NetworkManager
) : TaskRepository {
    override suspend fun getTasks(): List<Task> {
        return if (networkManager.hasInternet()) {
            val taskEntities = apiService.getTasks().map { it.mapToEntity() }

            taskDao.insertTasks(taskEntities)

            taskEntities.map { it.mapToDomain() }
        } else {
            taskDao.getTasks().map { it.mapToDomain() }
        }
    }
}