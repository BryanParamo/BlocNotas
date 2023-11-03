package com.example.proyecto_notas

import android.app.Application
import android.content.res.Configuration
import android.preference.PreferenceManager
import androidx.room.Room
import com.example.proyecto_notas.data.noteDatabase
import java.util.*

class NoteApp : Application()
{
    val room = Room.databaseBuilder(this, noteDatabase::class.java, "note" ).build()
}