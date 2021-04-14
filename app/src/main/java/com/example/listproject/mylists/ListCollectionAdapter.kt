package com.example.listproject.mylists

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listproject.databinding.CardLayoutBinding
import com.example.listproject.mylists.data.Task

class ListCollectionAdapter(
    private var lists:List<Task>,
    private val onTaskClicked:(Task) -> Unit,
    private val removeList:(Task) -> Unit) :
    RecyclerView.Adapter<ListCollectionAdapter.ViewHolder>(){

    class ViewHolder(val binding:CardLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(task:Task, onTaskClicked: (Task) -> Unit, removeList: (Task) -> Unit){
            binding.title.text = task.title

            binding.card.setOnClickListener{
                onTaskClicked(task)
            }
            binding.deleteListBtn.setOnClickListener {
                removeList(task)
            }
        }
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = lists[position]
        holder.bind(task,onTaskClicked,removeList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    public fun updateCollection(newLists:List<Task>){
        lists = newLists
        notifyDataSetChanged()
    }
}