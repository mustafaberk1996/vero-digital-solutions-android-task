package com.example.verodigitalsolutionandroidtask.domain.usecase

import com.example.verodigitalsolutionandroidtask.domain.repository.TaskRepository
import com.example.verodigitalsolutionandroidtask.ui.main.MainUiState
import javax.inject.Inject

class FetchTasks @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(): MainUiState  {
         return runCatching {
            MainUiState.TaskList(taskRepository.getTasks())
        }.getOrElse { throwable-> MainUiState.Error(throwable) }
    }
}