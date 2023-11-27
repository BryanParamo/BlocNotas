package com.example.proyecto_notas.model

import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction

/*
data class Note_reminders(

    @Embedded val user: Note,
    @Relation(
        parentColumn = "userId",
        entityColumn = "NoteId"
    )
    val reminders: List<Reminder>

)
 */