package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.FragmentEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditFragment : Fragment() {
    val oldNotes by navArgs<EditFragmentArgs>()
    lateinit var binding: FragmentEditBinding
    var priority: String = "1"
    val viewModel: NotesViewModel by viewModels()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {


        setHasOptionsMenu(true)

        binding = FragmentEditBinding.inflate(layoutInflater, container,false)

        binding.eEditTitle.setText(oldNotes.data.title)
        binding.eEditSubTitle.setText(oldNotes.data.subTitle)
        binding.eEditNotes.setText(oldNotes.data.notes)

        when(oldNotes.data.priority){
            "1" -> {
                priority = "1"
                binding.eGreen.setImageResource(R.drawable.ic_done)
                binding.eYellow.setImageResource(0)
                binding.eRed.setImageResource(0)
            }
            "2" -> {
                priority = "2"
                binding.eYellow.setImageResource(R.drawable.ic_done)
                binding.eGreen.setImageResource(0)
                binding.eRed.setImageResource(0)
            }
            "3" -> {
                priority = "3"
                binding.eRed.setImageResource(R.drawable.ic_done)
                binding.eYellow.setImageResource(0)
                binding.eGreen.setImageResource(0)
            }
        }


        binding.eBtnSaveNotes.setOnClickListener {
            updateNotes(it)
        }



        return binding.root
    }

    private fun updateNotes(it: View?) {

        val title = binding.eEditTitle.text.toString()
        val subTitle = binding.eEditSubTitle.text.toString()
        val notes = binding.eEditNotes.text.toString()
        val notesDate: CharSequence = android.text.format.DateFormat.format("MMMM d, yyyy ", Date().time)


        val data = Notes(oldNotes.data.id, title, subTitle, notes, notesDate.toString(), priority)
        viewModel.updateNotes(data)
        Toast.makeText(requireContext(), "Notes Updated Successfully", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.action_editFragment_to_homeFragment)


    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menuDelete){
            val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)
            bottomSheet.show()


            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(oldNotes.data.id!!)
                bottomSheet.dismiss()

                val action  = EditFragmentDirections.actionEditFragmentToHomeFragment()
                findNavController().navigate(action)
                Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show()

            }

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }


        }
        return super.onOptionsItemSelected(item)
    }

}
