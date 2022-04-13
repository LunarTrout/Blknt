package com.example.blknt.activity

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.example.blknt.R
import com.example.blknt.room.AppDatabase
import com.example.blknt.room.db.NotesDbEntity
import java.text.SimpleDateFormat
import java.util.*

class CreateNoteActivity : AppCompatActivity() {
    lateinit var edTitle: EditText
    lateinit var edContent: EditText
    lateinit var btnSend: Button

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        edTitle = findViewById(R.id.edTitle)
        edContent = findViewById(R.id.edContent)
        btnSend = findViewById(R.id.btnSend)

        val db = Room.databaseBuilder(applicationContext
            , AppDatabase::class.java
            , "notesdb").fallbackToDestructiveMigration().allowMainThreadQueries().build()

        btnSend.setOnClickListener {
            try {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
                db.getNotesDao().createNote(
                    NotesDbEntity(
                        0,
                        edTitle.text.toString(),
                        edContent.text.toString(),
                        sdf.format(Date()).toString()
                    )
                )

                Toast.makeText(this, "Заметка успешно добавлена!", Toast.LENGTH_SHORT).show()

            finish()
            } catch (e: SQLiteException) {
                Toast.makeText(this, "Ошибка уникальности!Измените название заметки.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}