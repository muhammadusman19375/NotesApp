package com.example.todoapp.dao

import androidx.room.*
import com.example.todoapp.model.NotesModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes")
    fun getNotes(): Flow<List<NotesModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNotes(notes: NotesModel)

    @Query("DELETE FROM Notes WHERE id=:id")
    suspend fun deleteNotes(id: Int)

    @Update
    suspend fun updateNotes(notes: NotesModel)

}