package com.example.todoapp.repository

import com.example.todoapp.dao.NotesDao
import com.example.todoapp.model.NotesModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(private val notesDao: NotesDao) {

    fun getNotes(): Flow<List<NotesModel>> {
        return notesDao.getNotes()
    }

    suspend fun createNotes(note: NotesModel) {
        notesDao.createNotes(note)
    }

    suspend fun updateNotes(notes: NotesModel) {
        notesDao.updateNotes(notes)
    }

    suspend fun deleteNotes(id: Int) {
        notesDao.deleteNotes(id)
    }

}