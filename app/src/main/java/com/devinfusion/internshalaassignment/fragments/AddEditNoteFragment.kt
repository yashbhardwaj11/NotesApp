package com.devinfusion.internshalaassignment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.devinfusion.internshalaassignment.NoteViewModel
import com.devinfusion.internshalaassignment.NoteViewModelFactory
import com.devinfusion.internshalaassignment.NotesApp
import com.devinfusion.internshalaassignment.databinding.FragmentAddEditNoteBinding
import com.devinfusion.internshalaassignment.models.Note

class AddEditNoteFragment : Fragment() {
    private val noteViewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((requireActivity().application as NotesApp).repository)
    }
    private lateinit var binding: FragmentAddEditNoteBinding
    private var noteId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt("note_id")

        noteId?.let {
            noteViewModel.getNoteById(it).observe(viewLifecycleOwner) { note ->
                note?.let {
                    binding.editTextTitle.setText(it.title)
                    binding.editTextContent.setText(it.content)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    private fun saveNote() {
        val title = binding.editTextTitle.text.toString().trim()
        val content = binding.editTextContent.text.toString().trim()

        if (title.isNotEmpty() && content.isNotEmpty()) {
            val note = noteId?.let {
                Note(id = it, title = title, content = content)
            } ?: Note(title = title, content = content)

            if (noteId == null) {
                noteViewModel.insert(note)
            } else {
                noteViewModel.update(note)
            }

            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
