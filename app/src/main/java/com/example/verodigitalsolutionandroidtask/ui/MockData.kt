package com.example.verodigitalsolutionandroidtask.ui

import com.example.verodigitalsolutionandroidtask.domain.Task

object MockData {

    val tasks = listOf(
        Task(
            task = "Task-001",
            title = "Prepare Reports",
            description = "Create weekly performance and analysis report for management.",
            colorCode = "#FF5722"
        ),
        Task(
            task = "Task-002",
            title = "Design Homepage",
            description = "Update UI to match new brand style and improve user experience.",
            colorCode = "#3F51B5"
        ),
        Task(
            task = "Task-003",
            title = "Fix Login Bug",
            description = "Resolve user session expiration issue found on Android 14.",
            colorCode = "#4CAF50"
        ),
        Task(
            task = "Task-004",
            title = "Database Cleanup",
            description = "Remove unused tables and optimize query performance.",
            colorCode = "#FFC107"
        ),
        Task(
            task = "Task-005",
            title = "Write Documentation",
            description = "Add API documentation for authentication and task modules.",
            colorCode = "#9C27B0"
        ),
        Task(
            task = "Task-006",
            title = "Security Review",
            description = "Perform security audit on token handling and encryption.",
            colorCode = "#E91E63"
        )
    )


}