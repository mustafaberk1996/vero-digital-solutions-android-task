package com.example.verodigitalsolutionandroidtask.domain.repository

import com.example.verodigitalsolutionandroidtask.domain.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    var allTasks: Flow<List<Task>>
    suspend fun fetchAndSaveTasks()
}