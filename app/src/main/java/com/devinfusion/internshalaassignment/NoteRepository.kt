package com.devinfusion.internshalaassignment

import com.devinfusion.internshalaassignment.daos.NoteDao
import com.devinfusion.internshalaassignment.models.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao : NoteDao) {
    val allNotes : Flow<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun update(note: Note) {
        noteDao.update(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }
}