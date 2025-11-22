package com.example.verodigitalsolutionandroidtask.ui.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RefreshWorker @Inject constructor(
    context: Context,
    workerParameters: WorkerParameters
) :
    Worker(context, workerParameters) {

        private val job = SupervisorJob()
        private val coroutineScope = CoroutineScope(Dispatchers.IO + job)
        override fun doWork(): Result {
            coroutineScope.launch {
                //fetchTasks(fetchType = FetchType.WORKER)
                Timber.Forest.d("Refresh worker triggered!")
            }
            return Result.success()
        }

        override fun onStopped() {
            super.onStopped()
            job.cancel()
        }

}