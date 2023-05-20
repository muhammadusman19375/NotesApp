package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.callback.HomeCallback
import com.example.todoapp.databinding.ListItemNotesBinding
import com.example.todoapp.model.NotesModel
import com.example.todoapp.utils.NotesPriority

class NotesAdapter(private var dataList: ArrayList<NotesModel>, private val homeCallback: HomeCallback) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                homeCallback.onItemClick(adapterPosition)
            }
        }

        fun bind(data: NotesModel) {
            binding.tvTitle.text = data.title
            binding.tvSubTitle.text = data.subTitle
            binding.tvDate.text = data.date

            when (data.priority) {
                NotesPriority.LOW -> {
                    binding.viewPriority.setBackgroundResource(R.drawable.green_dot)
                }
                NotesPriority.MEDIUM -> {
                    binding.viewPriority.setBackgroundResource(R.drawable.yellow_dot)
                }
                NotesPriority.HIGH -> {
                    binding.viewPriority.setBackgroundResource(R.drawable.red_dot)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }
}