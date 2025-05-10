package net.yigitak.todoapp.models

import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Subtask(
    var id: String = ObjectId().toHexString(),

    var title: String? = null,
    var description: String? = null,
    var completed: Boolean = false,

    var timeCreated: LocalDateTime = LocalDateTime.now()
)
