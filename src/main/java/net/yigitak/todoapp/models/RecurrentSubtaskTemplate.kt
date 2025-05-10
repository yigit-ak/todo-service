package net.yigitak.todoapp.models

import jakarta.validation.constraints.NotBlank

data class RecurrentSubtaskTemplate(

    @field:NotBlank(message = "Subtask title cannot be blank.")
    val title: String,

    val description: String? = null
)
