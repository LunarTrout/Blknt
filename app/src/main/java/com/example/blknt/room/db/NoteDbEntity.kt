package com.example.blknt.room.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "Notes",

    indices = [
        Index("title", unique = true),
    ]
)
data class NotesDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val title: String,
    @ColumnInfo val content: String,
    @ColumnInfo val date: String

) {}