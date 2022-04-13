package com.example.blknt.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.example.blknt.R
import com.example.blknt.room.AppDatabase
import java.text.SimpleDateFormat
import java.util.*

class EditNoteActivity : AppCompatActivity() {
    lateinit var btnSave: Button
    lateinit var btnDelete: Button
    lateinit var edTitle: EditText
    lateinit var edContent: EditText
    lateinit var layoutDelete: ConstraintLayout
    lateinit var darkScreen: RelativeLayout
    lateinit var btnYes: Button
    lateinit var btnNo:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)
        edTitle = findViewById(R.id.edTitle2)
        edContent = findViewById(R.id.edContent2)
        layoutDelete = findViewById(R.id.layoutDelete)
        darkScreen = findViewById(R.id.darkScreen)
        btnYes = findViewById(R.id.btnYes)
        btnNo = findViewById(R.id.btnNo)

        val id = intent.getStringExtra("id").toString()
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        edTitle.setText(title)
        edContent.setText(content)

        if (intent.getBooleanExtra("edit", true)) {
            editTrue(id)
        } else {
            editFalse(intent)
        }


    }

    @SuppressLint("SimpleDateFormat")
    fun editTrue(id: String) {


        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "notesdb"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()


        btnSave.setOnClickListener {
            try {
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")

                db.getNotesDao().updateById(
                    id.toLong(),
                    edTitle.text.toString(),
                    edContent.text.toString(),
                    sdf.format(Date()).toString() + "(ред.)"
                )

                finish()

            } catch (e: SQLiteException) {
                Toast.makeText(this, "Произошла ошибка сохранения!", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            btnDelete.visibility = View.GONE
            btnSave.visibility = View.GONE

            layoutDelete.visibility = View.VISIBLE
            darkScreen.visibility = View.VISIBLE


            edTitle.isEnabled = false
            edContent.isEnabled = false

            btnYes.setOnClickListener {
                try {
                    db.getNotesDao().deleteNote(id.toLong())
                    Toast.makeText(this, "Заметка успешно удалена!", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: SQLiteException) {
                    Toast.makeText(this, "Что-то пошло не так...", Toast.LENGTH_SHORT).show()
                }

            }
            btnNo.setOnClickListener {
                btnDelete.visibility = View.VISIBLE
                btnSave.visibility = View.VISIBLE
                layoutDelete.visibility = View.GONE
                darkScreen.visibility = View.GONE
                edTitle.isEnabled = true
                edContent.isEnabled = true
            }

        }

        db.close()
    }

    fun editFalse(intent: Intent) {

        edTitle.isEnabled = false
        edContent.isEnabled = false

        edTitle.hint = ""
        edContent.hint = "Тут должен быть ваш текст..."

        val btnToEdit = findViewById<Button>(R.id.btnToEdit)
        btnToEdit.visibility = View.VISIBLE
        btnSave.visibility = View.INVISIBLE
        btnDelete.visibility = View.INVISIBLE


        btnToEdit.setOnClickListener {
            intent.removeExtra("edit")
            intent.putExtra("edit", true)

            edTitle.hint = "Введите название заметки:"
            edContent.hint = "Введите текст заметки:"
            finish()
            startActivity(intent)
        }
    }

}

