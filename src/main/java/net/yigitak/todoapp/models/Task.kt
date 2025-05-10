package net.yigitak.todoapp.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDate
import java.time.LocalDateTime

@Document("tasks")
data class Task(
    @Id
    var id: String? = null,

    @JsonIgnore
    @DocumentReference
    var owner: User? = null,

    @DocumentReference
    var tag: Tag? = null,

    @DocumentReference
    var recurrence: Recurrence? = null,

    var subtasks: List<Subtask>? = null,
    var title: String? = null,
    var description: String? = null,
    var starred: Boolean = false,
    var completed: Boolean = false,
    var dateAssigned: LocalDate? = null,
    var dateDue: LocalDate? = null,

    @JsonIgnore
    var timeCreated: LocalDateTime = LocalDateTime.now()
)
