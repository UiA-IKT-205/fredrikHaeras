package com.example.listproject.mylists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listproject.databinding.ActivityListDetailsBinding
import com.example.listproject.databinding.ListDetailCardBinding
import com.example.listproject.mylists.data.ListDetails
import com.example.listproject.mylists.data.Task

class ListDetailsCollectionAdapter(
    private var items:List<ListDetails>,
    private val removeItem:(ListDetails) -> Unit)
    : RecyclerView.Adapter<ListDetailsCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListDetailCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(items:ListDetails, removeItem:(ListDetails) -> Unit){
            binding.item.text = items.item

            binding.deleteItemBtn.setOnClickListener {
                removeItem(items)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, removeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return ViewHolder(ListDetailCardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    public fun updateCollection(newItem:List<ListDetails>){
        items = newItem
        notifyDataSetChanged()
    }
}