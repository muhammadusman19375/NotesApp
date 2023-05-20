package com.example.todoapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.NotesAdapter
import com.example.todoapp.callback.HomeCallback
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.model.NotesModel
import com.example.todoapp.utils.NotesPriority
import com.example.todoapp.viewModel.HomeVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: NotesAdapter
    private lateinit var searchView: SearchView
    private val homeVM: HomeVM by viewModels()
    private val notesList: ArrayList<NotesModel> = ArrayList()
    private var currentPriority: NotesPriority? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeVM.getNotes()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        setListeners()
        setAdapter()
        setNotesObserver()
        setupMenu()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListeners() {
        binding.btnAddNotes.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreateNotesFragment()
            findNavController().navigate(action)
        }

        binding.ivFilter.setOnClickListener {
            currentPriority = null
            filterNotes(searchView.query.toString())
        }

        binding.tvFilterHigh.setOnClickListener {
            currentPriority = NotesPriority.HIGH
            filterNotes(searchView.query.toString())
        }

        binding.tvFilterMedium.setOnClickListener {
            currentPriority = NotesPriority.MEDIUM
            filterNotes(searchView.query.toString())
        }

        binding.tvFilterLow.setOnClickListener {
            currentPriority = NotesPriority.LOW
            filterNotes(searchView.query.toString())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setNotesObserver() {
        homeVM.getNotes.observe(viewLifecycleOwner) { allNotesList ->
            notesList.clear()
            notesList.addAll(allNotesList)
            Log.d("setNotesObserver", "setNotesObserver: ${notesList.size}")
            adapter.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        val layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvNotes.layoutManager = layoutManager
        Log.d("setAdapter", "setAdapter: ${notesList.size}")
        adapter = NotesAdapter(notesList, object : HomeCallback {
            override fun onItemClick(position: Int) {
                val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(notesList[position])
                findNavController().navigate(action)
            }
        })
        binding.rvNotes.adapter = adapter
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val item = menu.findItem(R.id.item_search)
                searchView = item.actionView as SearchView
                searchView.queryHint = "Search notes....."
                setSearchListener()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setSearchListener() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterNotes(newText!!)
                return true
            }
        })
    }

    private fun filterNotes(text: String) {
        val filteredNotesList = homeVM.allNotes.filter { notesModel ->
            (notesModel.title.lowercase().contains(text.lowercase()) ||
                    notesModel.subTitle.lowercase().contains(text.lowercase()) ||
                    notesModel.description.lowercase().contains(text.lowercase())) &&
                    (currentPriority == null || notesModel.priority == currentPriority)
        }
        homeVM.saveNotes(filteredNotesList as ArrayList)
    }

}