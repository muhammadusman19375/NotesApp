package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.FragmentCreateNotesBinding
import java.lang.String.format
import java.text.DateFormat
import java.util.*

class CreateNotesFragment : Fragment() {
    lateinit var binding: FragmentCreateNotesBinding
    var priority: String = "1"
    val viewModel: NotesViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        binding = FragmentCreateNotesBinding.inflate(layoutInflater,container,false)
        binding.cGreen.setImageResource(R.drawable.ic_done)

        binding.cGreen.setOnClickListener {
            priority = "1"
            binding.cGreen.setImageResource(R.drawable.ic_done)
            binding.cYellow.setImageResource(0)
            binding.cRed.setImageResource(0)
        }

        binding.cYellow.setOnClickListener {
            priority = "2"
            binding.cYellow.setImageResource(R.drawable.ic_done)
            binding.cGreen.setImageResource(0)
            binding.cRed.setImageResource(0)
        }

        binding.cRed.setOnClickListener {
            priority = "3"
            binding.cRed.setImageResource(R.drawable.ic_done)
            binding.cYellow.setImageResource(0)
            binding.cGreen.setImageResource(0)
        }


        binding.cBtnSaveNotes.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it: View?) {

        val title = binding.cEditTitle.text.toString()
        val subTitle = binding.cEditSubTitle.text.toString()
        val notes = binding.cEditNotes.text.toString()
        val notesDate: CharSequence = android.text.format.DateFormat.format("MMMM d, yyyy ",Date().time)


        val data = Notes(null, title, subTitle, notes, notesDate.toString(), priority)
        viewModel.insertNotes(data)
        Toast.makeText(requireContext(), "Notes Created Successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)


    }
}