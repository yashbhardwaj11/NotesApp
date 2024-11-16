package com.devinfusion.internshalaassignment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.devinfusion.internshalaassignment.NoteAdapter
import com.devinfusion.internshalaassignment.NoteViewModel
import com.devinfusion.internshalaassignment.NoteViewModelFactory
import com.devinfusion.internshalaassignment.NotesApp
import com.devinfusion.internshalaassignment.R
import com.devinfusion.internshalaassignment.databinding.FragmentNoteListBinding
import com.devinfusion.internshalaassignment.models.Note
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NoteListFragment : Fragment(), NoteAdapter.OnItemClickListener {
    private val noteViewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((requireActivity().application as NotesApp).repository)
    }
    private lateinit var binding: FragmentNoteListBinding
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        observeViewModel()

        binding.buttonAddNote.setOnClickListener {
            parentFragmentManager.commit {
                replace(R.id.fragment_container, AddEditNoteFragment())
                addToBackStack(null)
            }
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(this)
        binding.recyclerView.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { notes ->
            notes?.let {
                noteAdapter.setNotes(it)
                toggleNoNotesMessage(it.isEmpty())
            }
        })
    }

    private fun toggleNoNotesMessage(show: Boolean) {
        if (show) {
            binding.textNoNotes.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.textNoNotes.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(note: Note) {
        showNoteOptionsDialog(note)
    }

    private fun showNoteOptionsDialog(note: Note) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Choose an action")
            .setItems(arrayOf("Update", "Delete")) { dialog, which ->
                when (which) {
                    0 -> navigateToAddEditNoteFragment(note)
                    1 -> deleteNote(note)
                }
            }
            .show()
    }

    private fun navigateToAddEditNoteFragment(note: Note) {
        val fragment = AddEditNoteFragment().apply {
            arguments = Bundle().apply {
                putInt("note_id", note.id)
            }
        }
        parentFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }

    private fun deleteNote(note: Note) {
        noteViewModel.delete(note)
    }
}
