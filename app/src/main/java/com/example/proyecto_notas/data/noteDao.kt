package com.example.proyecto_notas.data

import androidx.room.*
import com.example.proyecto_notas.model.Note

//import com.example.proyecto_notas.model.Note_reminders

@Dao
interface noteDao {

    @Insert
     fun insert(note:Note)

     @Query("SELECT MAX(id) FROM Note")
     fun getMaxID(): Int

    @Query("SELECT * FROM Note WHERE type=1 ORDER BY date DESC")
     fun getAllTasks(): List<Note>

    @Query("SELECT * FROM Note WHERE type=2 ORDER BY id DESC")
    fun getAllNotes(): List<Note>

    @Query("SELECT * FROM Note WHERE id= :id")
     fun getById(id: Int) : List<Note>

    @Query("SELECT * FROM Note WHERE title LIKE :title AND type=:type OR description LIKE :description AND type=:type")
    fun getByTitleDescription(title: String, description: String, type:Int) : List<Note>


    @Query("UPDATE Note set title = :title, description = :description, date = :date, hour = :hour, completed = :completed WHERE id = :id")
    fun updateTask(title: String,description: String,date: String,hour: String,completed: Boolean,id: Int)

    @Query("UPDATE Note set title = :title, description = :description WHERE id = :id")
    fun updateNote(title: String,description:String, id: Int)


     @Delete
     fun deleteNote(note: Note)

    @Query("INSERT INTO lastID (last) VALUES(:id)")
    fun insertLastID(id:Int)

    @Query("SELECT * FROM lastID ORDER BY ID DESC LIMIT 1")
    fun getLastID() : Int

    /*
    @Transaction
    @Query("SELECT * FROM Note")
    suspend fun getNoteWithReminders(): LiveData<List<Note_reminders>>
     */
}