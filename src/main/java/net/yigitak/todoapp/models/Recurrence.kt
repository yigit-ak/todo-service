package net.yigitak.todoapp.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDate
import java.time.LocalDateTime

@Document("recurrences")
data class Recurrence(
    @Id
    var id: String? = null,

    var ownerId: String,

    @DocumentReference
    var tag: Tag? = null,

    @DocumentReference
    var tasks: Set<Task>? = null,

    var taskTemplate: RecurrentTaskTemplate? = null,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
    var lastOccurrence: LocalDate? = null,
    var period: Int = 0,

    var timeCreated: LocalDateTime = LocalDateTime.now()
) {
    val isForever: Boolean
        get() = endDate == null
}
