package com.example.proyecto_notas.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title: String,
    val description: String,
    val type: Int,
    val date: String,
    val hour: String,
    val completed : Boolean

){
    constructor(title: String, description: String, type: Int,date: String,hour: String, completed: Boolean) : this(0, title, description, type,date,hour, completed)


}