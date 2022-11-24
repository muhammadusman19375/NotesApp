package com.example.notesapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.Dao.NotesDao
import com.example.notesapp.Model.Notes


@Database(entities = [Notes::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun myNotesDao(): NotesDao

    companion object{
        @Volatile
        var INSTANCE: NotesDatabase? = null

        fun getDatabaseInstance(context: Context): NotesDatabase{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,NotesDatabase::class.java, "Notes").allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }

    }

}