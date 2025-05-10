package net.yigitak.todoapp.models

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

data class RecurrentTaskTemplate(

    @field:NotBlank(message = "Task title cannot be blank.")
    val title: String,

    @field:Valid
    val subtasks: List<RecurrentSubtaskTemplate>,

    val description: String? = null
)
