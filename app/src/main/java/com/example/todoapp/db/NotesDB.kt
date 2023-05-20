package com.example.todoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.dao.NotesDao
import com.example.todoapp.model.NotesModel

@Database(entities = [NotesModel::class], version = 1, exportSchema = false)
abstract class NotesDB: RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

}