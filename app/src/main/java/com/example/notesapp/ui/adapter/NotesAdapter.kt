package com.example.notesapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Model.Notes
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.ui.fragments.HomeFragment
import com.example.notesapp.ui.fragments.HomeFragmentDirections

class NotesAdapter(val requireContext: Context, var notesList: List<Notes>): RecyclerView.Adapter<NotesAdapter.notesViewHolder>(),Filterable {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): notesViewHolder {
        return notesViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: notesViewHolder, position: Int) {
        holder.binding.notesTitle.text = notesList[position].title.toString()
        holder.binding.notesSubTitle.text = notesList[position].subTitle.toString()
        holder.binding.notesDate.text = notesList[position].date.toString()

        when(notesList[position].priority){
            "1" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
            }
            "2" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
            }
            "3" -> {
                holder.binding.viewPriority.setBackgroundResource(R.drawable.red_dot)
            }
        }

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditFragment(notesList[position])
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun filtering(newFilteredList: ArrayList<Notes>) {
        notesList = newFilteredList
        notifyDataSetChanged()
    }

    class notesViewHolder(val binding: ItemNotesBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}