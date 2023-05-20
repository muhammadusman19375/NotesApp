package com.example.todoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.NotesModel
import com.example.todoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val repository: Repository) : ViewModel() {

    val allNotes: ArrayList<NotesModel> = ArrayList()

    private val _getNotes: MutableLiveData<List<NotesModel>> = MutableLiveData()
    val getNotes: LiveData<List<NotesModel>> = _getNotes

    fun getNotes() = viewModelScope.launch {
        repository.getNotes().collect { notesList ->
            _getNotes.value = notesList
            allNotes.clear()
            allNotes.addAll(notesList)
        }
    }

    fun saveNotes(notesList: ArrayList<NotesModel>) {
        _getNotes.value = notesList
    }
}