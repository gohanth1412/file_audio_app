package com.example.editfiledemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.editfiledemo.databinding.ViewItemBinding

class MainAdapter(
    private val list: List<AudioModel>,
    private val onItemClick: (AudioModel) -> Unit
) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ViewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.apply {
            tvFileName.text = currentItem.name
            tvDateAdd.text = currentItem.date
            tvSize.text = currentItem.size
        }
        holder.binding.layoutItem.setOnLongClickListener {
            onItemClick(currentItem)
            true
        }
    }
}