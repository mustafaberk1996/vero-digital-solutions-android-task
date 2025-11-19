package com.example.verodigitalsolutionandroidtask.domain.usecase

import com.example.verodigitalsolutionandroidtask.domain.repository.TaskRepository
import com.example.verodigitalsolutionandroidtask.ui.main.MainUiState
import javax.inject.Inject

class FetchTasks @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(): MainUiState  {
         return runCatching {
            val result = taskRepository.getTasks()
            if (result.isEmpty()) MainUiState.Empty else MainUiState.TaskList(result)
        }.getOrElse { throwable-> MainUiState.Error(throwable) }
    }
}