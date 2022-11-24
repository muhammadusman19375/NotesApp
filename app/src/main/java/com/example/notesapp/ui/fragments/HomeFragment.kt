package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.ViewModel.NotesViewModel
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.ui.adapter.NotesAdapter

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        viewModel.getNotes().observe(viewLifecycleOwner, Observer { notesList->
            oldMyNotes = notesList as ArrayList<Notes>
            binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = NotesAdapter(requireContext(), notesList)
            binding.rcvAllNotes.adapter = adapter
        })


        binding.allNotes.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner, Observer { notesList->
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner, Observer { notesList->
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner, Observer { notesList->
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }

        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner, Observer { notesList->
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rcvAllNotes.adapter = adapter
            })
        }



        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.app_bar_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint = "Enter Notes Here..."

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                NotesFiltering(newText)
                return true
            }
        })


        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun NotesFiltering(newText: String?) {

        val newFilteredList = arrayListOf<Notes>()
        for(item in oldMyNotes){
            if(item.title.lowercase().contains(newText!!.lowercase()) || item.title.uppercase().contains(newText!!.uppercase())
                || item.subTitle.lowercase().contains(newText!!.lowercase()) || item.subTitle.uppercase().contains(newText!!.uppercase())){
                newFilteredList.add(item)
            }
        }
        adapter.filtering(newFilteredList)

    }

}
