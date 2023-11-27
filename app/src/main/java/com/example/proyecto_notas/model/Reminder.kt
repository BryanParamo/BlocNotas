package com.example.proyecto_notas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reminder (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var noteID: Int,
    var date: String,
    var time: String
)
{
    constructor(noteID: Int, date: String, time: String ) : this(0, noteID, date, time)
}