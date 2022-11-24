package com.example.notesapp.Model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "Notes")
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val subTitle: String,
    val notes: String,
    val date: String,
    val priority: String
) : Parcelable