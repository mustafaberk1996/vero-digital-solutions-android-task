package com.example.verodigitalsolutionandroidtask.domain.repository

import com.example.verodigitalsolutionandroidtask.domain.Task

interface TaskRepository {

    suspend fun getTasks():List<Task>
}