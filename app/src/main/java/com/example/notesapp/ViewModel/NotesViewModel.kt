package com.example.notesapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notesapp.Database.NotesDatabase
import com.example.notesapp.Model.Notes
import com.example.notesapp.Repository.NotesRepository

class NotesViewModel(application: Application): AndroidViewModel(application) {

    val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun getNotes(): LiveData<List<Notes>> = repository.getNotes()

    fun getHighNotes(): LiveData<List<Notes>> = repository.getHighNotes()

    fun getMediumNotes(): LiveData<List<Notes>> = repository.getMediumNotes()

    fun getLowNotes(): LiveData<List<Notes>> = repository.getLowNotes()


    fun insertNotes(notes: Notes){
        repository.insertNotes(notes)
    }

    fun deleteNotes(id: Int){
        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        repository.updateNotes(notes)
    }


}