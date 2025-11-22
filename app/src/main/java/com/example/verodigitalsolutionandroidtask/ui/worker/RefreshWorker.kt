package com.example.verodigitalsolutionandroidtask.ui.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.verodigitalsolutionandroidtask.domain.model.FetchType
import com.example.verodigitalsolutionandroidtask.domain.usecase.FetchTasks
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class RefreshWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val fetchTasks: FetchTasks
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return runCatching {
            fetchTasks(fetchType = FetchType.WORKER)
            Timber.d("Refresh worker triggered!")
            return Result.success()
        }.onFailure {
            return Result.retry()
        }.getOrElse { Result.retry() }
    }

}