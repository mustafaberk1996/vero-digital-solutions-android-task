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
)


fun TaskEntity.mapToDomain(): Task{
    return Task(
        task = task.orEmpty(),
        title = title.orEmpty(),
        description = description.orEmpty(),
        colorCode = if (colorCode.isNullOrBlank())  "#FFFFFF" else colorCode
    )
}