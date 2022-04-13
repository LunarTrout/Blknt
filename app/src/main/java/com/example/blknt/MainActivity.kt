package com.example.blknt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.blknt.activity.CreateNoteActivity
import com.example.blknt.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    lateinit var btnSend: FloatingActionButton
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSend = findViewById(R.id.button)
        rv = findViewById(R.id.rvNotes)

        displayNotes(rv)

        btnSend.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        displayNotes(rv)
    }

    fun displayNotes(rv: RecyclerView){
        db = Room.databaseBuilder(applicationContext
            , AppDatabase::class.java
            , "notesdb").fallbackToDestructiveMigration().allowMainThreadQueries().build()

        val listOfNotes = db.getNotesDao().selectAll()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = CustomRecyclerAdapter(listOfNotes, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }


}