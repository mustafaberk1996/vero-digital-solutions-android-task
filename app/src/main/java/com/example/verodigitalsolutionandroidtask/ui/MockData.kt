package com.example.verodigitalsolutionandroidtask.ui

import com.example.verodigitalsolutionandroidtask.domain.Task

object MockData {

    val tasks = listOf(
        Task(
            task = "TASK-001",
            title = "Prepare Monthly Report",
            description = "Compile all department monthly reports and submit to management.",
            colorCode = "#FF5722",
            sort = 1,
            wageType = "Hourly",
            businessUnitKey = "BU001",
            businessUnit = "Finance",
            parentTaskID = "",
            preplanningBoardQuickSelect = "Reports",
            workingTime = "09:00-17:00",
            externalId = "EXT001",
            isAvailableInTimeTrackingKioskMode = true,
            isAbstract = false
        ),
        Task(
            task = "TASK-002",
            title = "Design Homepage",
            description = "Redesign homepage according to the new UI/UX guidelines.",
            colorCode = "#3F51B5",
            sort = 2,
            wageType = "Fixed",
            businessUnitKey = "BU002",
            businessUnit = "Design",
            parentTaskID = "",
            preplanningBoardQuickSelect = "Designs",
            workingTime = "10:00-18:00",
            externalId = "EXT002",
            isAvailableInTimeTrackingKioskMode = false,
            isAbstract = false
        ),
        Task(
            task = "TASK-003",
            title = "Fix Login Bug",
            description = "Investigate and fix the login failure occurring on Android 14.",
            colorCode = "#4CAF50",
            sort = 3,
            wageType = "Hourly",
            businessUnitKey = "BU003",
            businessUnit = "Development",
            parentTaskID = "",
            preplanningBoardQuickSelect = "Bugfix",
            workingTime = "09:00-17:00",
            externalId = "EXT003",
            isAvailableInTimeTrackingKioskMode = true,
            isAbstract = false
        ),
        Task(
            task = "TASK-004",
            title = "Security Review",
            description = "Perform full security audit for authentication module.",
            colorCode = "#E91E63",
            sort = 4,
            wageType = "Fixed",
            businessUnitKey = "BU004",
            businessUnit = "Security",
            parentTaskID = "",
            preplanningBoardQuickSelect = "Audit",
            workingTime = "08:00-16:00",
            externalId = "EXT004",
            isAvailableInTimeTrackingKioskMode = true,
            isAbstract = false
        )
    )



}