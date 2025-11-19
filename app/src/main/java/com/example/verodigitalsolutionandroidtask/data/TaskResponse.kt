package com.example.verodigitalsolutionandroidtask.data

import com.example.verodigitalsolutionandroidtask.data.local.entity.TaskEntity
import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("task") val task: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("sort") val sort: Int?,
    @SerializedName("wageType") val wageType: String?,
    @SerializedName("BusinessUnitKey") val businessUnitKey: String?,
    @SerializedName("businessUnit") val businessUnit: String?,
    @SerializedName("parentTaskID") val parentTaskID: String?,
    @SerializedName("preplanningBoardQuickSelect") val preplanningBoardQuickSelect: String?,
    @SerializedName("colorCode") val colorCode: String?,
    @SerializedName("workingTime") val workingTime: String?,
    @SerializedName("isAvailableInTimeTrackingKioskMode") val isAvailableInTimeTrackingKioskMode: Boolean?,
    @SerializedName("isAbstract") val isAbstract: Boolean?,
    @SerializedName("externalId") val externalId: String?
)

fun TaskResponse.mapToEntity(): TaskEntity{
    return TaskEntity(
        task = task,
        title = title,
        description = description,
        colorCode = colorCode,
        sort = sort,
        wageType = wageType,
        businessUnitKey = businessUnitKey,
        businessUnit = businessUnit,
        parentTaskID = parentTaskID,
        preplanningBoardQuickSelect = preplanningBoardQuickSelect,
        workingTime = workingTime,
        isAvailableInTimeTrackingKioskMode = isAvailableInTimeTrackingKioskMode,
        externalId = externalId,
        isAbstract = isAbstract
    )
}