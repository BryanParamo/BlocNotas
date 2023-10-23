package com.example.blocnotas.data


import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class lastID(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var last: Int
)