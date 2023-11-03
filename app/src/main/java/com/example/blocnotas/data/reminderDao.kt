package com.example.proyecto_notas.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.proyecto_notas.model.Note
import com.example.proyecto_notas.model.Reminder
import java.util.Date


@Dao
interface reminderDao {

    @Insert
    fun insert(reminder: Reminder)
    @Query("SELECT * FROM Reminder WHERE noteID=:id")
    fun getAllReminders(id: Int): MutableList<Reminder>

    @Query("DELETE FROM Reminder WHERE noteID = :id")
    fun deleteAllReminders(id: Int)

    @Delete
    fun deleteReminder(reminder:Reminder)

    @Query("SELECT MAX(ID) FROM reminder")
    fun getMaxId():Int

}