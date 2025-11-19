package com.example.verodigitalsolutionandroidtask.data

import com.example.verodigitalsolutionandroidtask.data.local.entity.TaskEntity
import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("task") val task: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("colorCode") val colorCode: String?,
)

fun TaskResponse.mapToEntity(): TaskEntity{
    return TaskEntity(
        task = task,
        title = title,
        description = description,
        colorCode = colorCode
    )
}