package ch.heigvd.daa_labo3.models

import androidx.room.Embedded
import androidx.room.Relation

data class NoteAndSchedule (
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "ownerId"
    )
    val schedule: Schedule?
)
