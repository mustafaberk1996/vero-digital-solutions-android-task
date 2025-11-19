package com.example.verodigitalsolutionandroidtask.data

import com.example.verodigitalsolutionandroidtask.domain.Task
import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("task") val task: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("sort") val sort: Int,
    @SerializedName("wageType") val wageType: String,
    @SerializedName("BusinessUnitKey") val businessUnitKey: String,
    @SerializedName("businessUnit") val businessUnit: String,
    @SerializedName("parentTaskID") val parentTaskID: String?,
    @SerializedName("preplanningBoardQuickSelect") val preplanningBoardQuickSelect: String?,
    @SerializedName("colorCode") val colorCode: String?,
    @SerializedName("workingTime") val workingTime: String?,
    @SerializedName("isAvailableInTimeTrackingKioskMode") val isAvailableInTimeTrackingKioskMode: Boolean,
    @SerializedName("isAbstract") val isAbstract: Boolean,
    @SerializedName("externalId") val externalId: String?
)

fun TaskResponse.mapToDomain(): Task{
    return Task(
        task = task,
        title = title,
        colorCode = if (colorCode.isNullOrBlank())  "#FFFFFF" else colorCode
    )
}