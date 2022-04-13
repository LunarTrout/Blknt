package com.example.blknt.room.db

import androidx.room.*
import com.example.blknt.room.Note

@Dao
interface NotesDao {
    @Query("SELECT* FROM NOTES")
    fun selectAll(): List<Note>

    @Insert
    fun createNote(notesDbEntity: NotesDbEntity)

    @Query("DELETE FROM Notes WHERE id = :id")
    fun deleteNote(id: Long)

    @Query("UPDATE Notes SET title = :title,  content = :content ,  date =  :date  WHERE id = :id")
    fun updateById(id: Long, title:String, content: String, date: String)

}