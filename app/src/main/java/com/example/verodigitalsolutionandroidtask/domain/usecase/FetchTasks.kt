package com.example.verodigitalsolutionandroidtask.domain.usecase

import com.example.verodigitalsolutionandroidtask.data.datastore.AppDataStore
import com.example.verodigitalsolutionandroidtask.domain.repository.TaskRepository
import javax.inject.Inject

class FetchTasks @Inject constructor(
    private val taskRepository: TaskRepository,
    private val dataStore: AppDataStore
) {
    suspend operator fun invoke()  {
        taskRepository.fetchAndSaveTasks()
        dataStore.saveLastFetchTime()
    }
}