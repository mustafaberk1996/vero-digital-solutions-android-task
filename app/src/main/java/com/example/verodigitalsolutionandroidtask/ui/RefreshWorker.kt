package com.example.verodigitalsolutionandroidtask.ui

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.verodigitalsolutionandroidtask.domain.model.FetchType
import com.example.verodigitalsolutionandroidtask.domain.usecase.FetchTasks
import javax.inject.Inject

class RefreshWorker @Inject constructor(
    context: Context,
    workerParameters: WorkerParameters,
    val fetchTasks: FetchTasks
) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        fetchTasks(fetchType = FetchType.WORKER)
        return Result.success()
    }

}