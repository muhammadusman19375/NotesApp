package com.example.notesapp.Repository

import androidx.lifecycle.LiveData
import com.example.notesapp.Dao.NotesDao
import com.example.notesapp.Model.Notes

class NotesRepository(private val dao: NotesDao) {

    fun getNotes(): LiveData<List<Notes>>{
        return dao.getNotes()
    }

    fun getHighNotes(): LiveData<List<Notes>> = dao.getHighNotes()

    fun getMediumNotes(): LiveData<List<Notes>> = dao.getMediumNotes()

    fun getLowNotes(): LiveData<List<Notes>> = dao.getLowNotes()

    fun insertNotes(notes: Notes){
        return dao.insertNotes(notes)
    }

    fun deleteNotes(id: Int){
        return dao.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        return dao.updateNotes(notes)
    }

}