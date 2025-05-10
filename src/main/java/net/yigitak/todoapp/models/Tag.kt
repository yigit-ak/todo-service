package net.yigitak.todoapp.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import java.time.LocalDateTime

@Document("tags")
data class Tag(
    @Id
    var id: String? = null,

    @JsonIgnore
    @DocumentReference
    var owner: User? = null,

    @JsonIgnore
    var timeCreated: LocalDateTime = LocalDateTime.now(),

    var title: String? = null,
    var colorCode: String? = null
) {
    constructor(tagId: String) : this(id = tagId)
}
