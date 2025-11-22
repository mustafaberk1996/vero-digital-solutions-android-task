package com.example.verodigitalsolutionandroidtask.data.repository

import com.example.verodigitalsolutionandroidtask.data.local.dao.TaskDao
import com.example.verodigitalsolutionandroidtask.data.local.entity.mapToDomain
import com.example.verodigitalsolutionandroidtask.data.network.model.mapToEntity
import com.example.verodigitalsolutionandroidtask.domain.model.Task
import com.example.verodigitalsolutionandroidtask.domain.repository.TaskRepository
import com.example.verodigitalsolutionandroidtask.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class TaskRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val taskDao: TaskDao
) : TaskRepository {

    override var allTasks: Flow<List<Task>> = taskDao.getTasks().map { it.map { it.mapToDomain() } }

    override suspend fun fetchAndSaveTasks() {
         apiService.getTasks().map { it.mapToEntity() } .also {
             taskDao.insertTasks(it)
         }
    }

}