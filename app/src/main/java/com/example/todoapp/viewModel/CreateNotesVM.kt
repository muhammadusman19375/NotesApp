package com.example.todoapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.NotesModel
import com.example.todoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNotesVM @Inject constructor(private val repository: Repository) : ViewModel() {

    fun createNotes(note: NotesModel) {
        viewModelScope.launch {
            repository.createNotes(note)
        }
    }

}