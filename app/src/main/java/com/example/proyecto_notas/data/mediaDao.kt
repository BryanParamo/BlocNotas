package com.example.proyecto_notas.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.proyecto_notas.model.Media

@Dao
interface mediaDao {
    @Insert
    fun insert(media:Media)
    @Query("SELECT * FROM Media WHERE noteID = :id")
    fun getAllMedia(id:Int): List<Media>
    @Query("DELETE FROM Media WHERE noteID = :id")
    fun deleteAllMedia(id:Int)
    @Delete
    fun deleteMedia(media: Media)
}