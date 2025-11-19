package com.example.verodigitalsolutionandroidtask.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.verodigitalsolutionandroidtask.domain.Task
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val task: String?,
    val title: String?,
    val description: String?,
    val colorCode: String?,
    val sort:Int?,
    val wageType:String?,
    val businessUnitKey: String?,
    val businessUnit: String?,
    val parentTaskID: String?,
    val preplanningBoardQuickSelect: String?,
    val workingTime: String?,
    val isAvailableInTimeTrackingKioskMode: Boolean?,
    val isAbstract: Boolean?,
    val externalId: String?
)

fun TaskEntity.mapToDomain(): Task{
    return Task(
        task = task.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        colorCode = if (colorCode.isNullOrBlank())  "#FFFFFF" else colorCode,
        sort = sort,
        wageType = wageType.orEmpty(),
        businessUnitKey = businessUnitKey.orEmpty(),
        businessUnit = businessUnit.orEmpty(),
        parentTaskID = parentTaskID.orEmpty(),
        preplanningBoardQuickSelect = preplanningBoardQuickSelect.orEmpty(),
        workingTime = workingTime.orEmpty(),
        isAvailableInTimeTrackingKioskMode = isAvailableInTimeTrackingKioskMode,
        externalId = externalId.orEmpty(),
        isAbstract = isAbstract
    )
}