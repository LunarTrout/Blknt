package com.example.blknt

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.blknt.activity.EditNoteActivity
import com.example.blknt.room.Note

class CustomRecyclerAdapter(private val notes: List<Note>, private val context: Context) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return MyHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val title = notes.get(position).title
        if (title.length > 10) {
            holder.tvTitle.setText(title.substring(0, 9) + "...")
        } else {
            holder.tvTitle.setText(title)
        }

        val content = notes.get(position).content
        if (content.length > 30) {
            holder.tvContent.setText(content.substring(0, 29) + "...")
        } else {
            holder.tvContent.setText(content)
        }

        holder.tvTime.setText(notes.get(position).date)


        val btnEdit = holder.itemView.findViewById<Button>(R.id.btnEdit)
        btnEdit.setOnClickListener {
            val intent = Intent(context, EditNoteActivity::class.java). apply {
                putExtra("edit",true)
                putExtra("id", notes.get(position).id.toString())
                putExtra("title", notes.get(position).title)
                putExtra("content", notes.get(position).content)
                putExtra("date", notes.get(position).date)
            }

            startActivity(context, intent, Bundle.EMPTY)
        }

        val btnNote = holder.itemView.findViewById<RelativeLayout>(R.id.note)
        btnNote.setOnClickListener {
            val intent = Intent(context, EditNoteActivity::class.java). apply {
            putExtra("edit",false)
            putExtra("id", notes.get(position).id.toString())
            putExtra("title", notes.get(position).title)
            putExtra("content", notes.get(position).content)
            putExtra("date", notes.get(position).date)
        }

        startActivity(context, intent, Bundle.EMPTY)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)

    }
}