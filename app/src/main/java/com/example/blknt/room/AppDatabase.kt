package com.example.blknt.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.blknt.room.db.NotesDao
import com.example.blknt.room.db.NotesDbEntity

@Database(
    version = 3,
    entities = [
        NotesDbEntity::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}