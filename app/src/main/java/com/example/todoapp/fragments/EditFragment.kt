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
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentEditBinding
import com.example.todoapp.databinding.LayoutBottomDeleteDialogBinding
import com.example.todoapp.model.NotesModel
import com.example.todoapp.utils.NotesPriority
import com.example.todoapp.viewModel.EditVM
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding
    private val args: EditFragmentArgs by navArgs()
    private val editVm: EditVM by viewModels()
    private lateinit var priority: NotesPriority

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        priority = args.data.priority
        setData()
        setListeners()
        setupMenu()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.item_delete -> {
                        showBottomSheet()
                        true
                    }
                    android.R.id.home -> {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showBottomSheet() {
        val bottomSheetBinding = LayoutBottomDeleteDialogBinding.inflate(layoutInflater)
        val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
        bottomSheet.setContentView(bottomSheetBinding.root)
        bottomSheet.show()

        bottomSheetBinding.tvYes.setOnClickListener {
            editVm.deleteNotes(args.data.id!!)
            bottomSheet.dismiss()
            Toast.makeText(requireContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        bottomSheetBinding.tvNo.setOnClickListener {
            bottomSheet.dismiss()
        }
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

        binding.btnUpdate.setOnClickListener {
            if (validateField()) {
                updateNotes()
                Toast.makeText(requireContext(), "Notes Updated Successfully", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateNotes() {
        val sdf = SimpleDateFormat("MMMM d, yyyy")
        val currentDate = sdf.format(Date())
        editVm.updateNotes(
            NotesModel(
                id = args.data.id,
                title = binding.etTitle.text.toString(),
                subTitle = binding.etSubTitle.text.toString(),
                description = binding.etDescription.text.toString(),
                date = currentDate,
                priority = priority
            )
        )
    }

    private fun setData() {
        binding.etTitle.setText(args.data.title)
        binding.etSubTitle.setText(args.data.subTitle)
        binding.etDescription.setText(args.data.description)

        when (args.data.priority) {
            NotesPriority.LOW -> {
                binding.ivLowPriority.setImageResource(R.drawable.ic_done)
                binding.ivMediumPriority.setImageResource(0)
                binding.ivHighPriority.setImageResource(0)
            }
            NotesPriority.MEDIUM -> {
                binding.ivMediumPriority.setImageResource(R.drawable.ic_done)
                binding.ivLowPriority.setImageResource(0)
                binding.ivHighPriority.setImageResource(0)
            }
            NotesPriority.HIGH -> {
                binding.ivHighPriority.setImageResource(R.drawable.ic_done)
                binding.ivLowPriority.setImageResource(0)
                binding.ivMediumPriority.setImageResource(0)
            }
        }
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
        if (binding.etDescription.text.isEmpty()) {
            binding.etDescription.error = "Description cannot be empty"
            return false
        }
        return true
    }

}