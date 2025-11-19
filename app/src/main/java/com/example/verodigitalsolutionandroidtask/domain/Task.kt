package com.example.verodigitalsolutionandroidtask.domain

data class Task(
    val task: String,
    val title: String,
    val description: String,
    val colorCode:String,
    val sort:Int?,
    val wageType:String,
    val businessUnitKey: String,
    val businessUnit: String,
    val parentTaskID: String,
    val preplanningBoardQuickSelect: String,
    val workingTime: String,
    val externalId: String,
    val isAvailableInTimeTrackingKioskMode: Boolean?,
    val isAbstract: Boolean?,
)

fun Task.filter(query: String): Boolean{
         return   title.contains(query, ignoreCase = true) ||
            description.contains(query, ignoreCase = true) ||
            task.contains(query, ignoreCase = true) ||
            colorCode.contains(query, ignoreCase = true) ||
            wageType.contains(query, ignoreCase = true) ||
            businessUnitKey.contains(query, ignoreCase = true) ||
            businessUnit.contains(query, ignoreCase = true) ||
            parentTaskID.contains(query, ignoreCase = true) ||
            preplanningBoardQuickSelect.contains(query, ignoreCase = true) ||
            workingTime.contains(query, ignoreCase = true) ||
            externalId.contains(query, ignoreCase = true)
}
