package com.example.todoapp.dbModule

import android.content.Context
import androidx.room.Room
import com.example.todoapp.dao.NotesDao
import com.example.todoapp.db.NotesDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotesDatabase {

    @Singleton
    @Provides
    fun providesNotesDB(@ApplicationContext context: Context): NotesDB {
        return Room.databaseBuilder(
            context,
            NotesDB::class.java,
            "NotesDB"
        ).build()
    }

    @Singleton
    @Provides
    fun providesNotesDao(db: NotesDB): NotesDao {
        return db.getNotesDao()
    }

}