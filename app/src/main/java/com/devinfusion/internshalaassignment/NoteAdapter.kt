package com.devinfusion.internshalaassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devinfusion.internshalaassignment.models.Note

class NoteAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var notesList = emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.bind(currentNote)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setNotes(notes: List<Note>) {
        notesList = notes
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val titleTextView: TextView = itemView.findViewById(R.id.text_view_title)
        private val contentTextView: TextView = itemView.findViewById(R.id.text_view_content)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(note: Note) {
            titleTextView.text = note.title
            contentTextView.text = note.content
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(notesList[position])
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note)
    }
}

