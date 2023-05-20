package com.example.todoapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapp.utils.NotesPriority
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Notes")
@Parcelize
data class NotesModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val subTitle: String,
    val description: String,
    val date: String,
    val priority: NotesPriority
) : Parcelable
