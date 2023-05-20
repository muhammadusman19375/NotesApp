package com.example.todoapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCreateNotesBinding
import com.example.todoapp.model.NotesModel
import com.example.todoapp.utils.NotesPriority
import com.example.todoapp.viewModel.CreateNotesVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateNotesFragment : Fragment() {
    private lateinit var binding: FragmentCreateNotesBinding
    private val createNotesVM: CreateNotesVM by viewModels()
    private lateinit var priority: NotesPriority

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        priority = NotesPriority.LOW
        binding.ivLowPriority.setImageResource(R.drawable.ic_done)
        setListeners()
        setupMenu()
    }

    private fun setListeners() {
        binding.ivLowPriority.setOnClickListener {
            priority = NotesPriority.LOW
            binding.ivLowPriority.setImageResource(R.drawable.ic_done)
            binding.ivMediumPriority.setImageResource(0)
            binding.ivHighPriority.setImageResource(0)
        }

        binding.ivMediumPriority.setOnClickListener {
            priority = NotesPriority.MEDIUM
            binding.ivMediumPriority.setImageResource(R.drawable.ic_done)
            binding.ivLowPriority.setImageResource(0)
            binding.ivHighPriority.setImageResource(0)
        }

        binding.ivHighPriority.setOnClickListener {
            priority = NotesPriority.HIGH
            binding.ivHighPriority.setImageResource(R.drawable.ic_done)
            binding.ivMediumPriority.setImageResource(0)
            binding.ivLowPriority.setImageResource(0)
        }

        binding.btnSaveNotes.setOnClickListener {
            if (validateField()) {
                createNotes()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createNotes() {
        val sdf = SimpleDateFormat("MMMM d, yyyy")
        val currentDate = sdf.format(Date())
        lifecycleScope.launch {
            createNotesVM.createNotes(
                NotesModel(
                    id = null,
                    title = binding.etTitle.text.toString(),
                    subTitle = binding.etSubTitle.text.toString(),
                    description = binding.etDescription.text.toString(),
                    date = currentDate,
                    priority = priority
                )
            )
        }
        Toast.makeText(requireContext(), "Notes Created Successfully", Toast.LENGTH_SHORT).show()
        setFieldsEmpty()
    }

    private fun validateField(): Boolean {
        if (binding.etTitle.text.isEmpty()) {
            binding.etTitle.error = "Title cannot be empty"
            return false
        }
        if (binding.etSubTitle.text.isEmpty()) {
            binding.etSubTitle.error = "SubTitle cannot be empty"
            return false
        }
        return true
    }

    private fun setFieldsEmpty() {
        binding.etTitle.setText("")
        binding.etSubTitle.setText("")
        binding.etDescription.setText("")
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home) {
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}