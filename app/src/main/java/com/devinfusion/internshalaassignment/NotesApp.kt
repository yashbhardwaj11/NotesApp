package com.devinfusion.internshalaassignment

import android.app.Application

class NotesApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}