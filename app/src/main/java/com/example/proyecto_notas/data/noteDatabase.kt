package com.example.proyecto_notas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyecto_notas.model.Media
import com.example.proyecto_notas.model.Note
import com.example.proyecto_notas.model.Reminder

@Database(entities = [Note::class, Reminder::class,lastID::class, Media::class], version = 4, exportSchema = false)
abstract class noteDatabase : RoomDatabase(){
    abstract fun noteDao(): noteDao
    abstract fun reminderDAO(): reminderDao
    abstract fun mediaDao(): mediaDao

    companion object {
        private var INSTANCE: noteDatabase? = null
        fun getDatabase(context: Context): noteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,noteDatabase::class.java, "note")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }



}