package net.yigitak.todoapp.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime

@Document("tags")
data class Tag(
    @Id
    var id: String? = null,

    @DocumentReference
    var ownerId: String,

    var timeCreated: LocalDateTime = LocalDateTime.now(),

    var title: String? = null,
    var colorCode: String? = null
)
