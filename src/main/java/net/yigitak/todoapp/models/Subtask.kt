package net.yigitak.todoapp.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId







































import java.time.LocalDateTime

data class Subtask(
    var id: String = ObjectId().toHexString(),





    var title: String? = null,
    var description: String? = null,
    var completed: Boolean = false,











    @JsonIgnore
    var timeCreated: LocalDateTime = LocalDateTime.now()
)
