package com.example.obscurascent

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.obscurascent.databinding.ViewholderCategoryBinding

class CategoryAdapter(
    private val context: Context,
    private val categories: List<String>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedPosition = 0

    inner class ViewHolder(val binding: ViewholderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.titleCat.text = category

        if (position == selectedPosition) {
            holder.binding.titleCat.setBackgroundResource(R.drawable.bg_chip_selected)
            holder.binding.titleCat.setTextColor(context.getColor(R.color.colorBackground))
        } else {
            holder.binding.titleCat.setBackgroundResource(R.drawable.bg_chip_unselected)
            holder.binding.titleCat.setTextColor(context.getColor(R.color.colorPrimary))
        }

        holder.binding.titleCat.setOnClickListener {
            val previous = selectedPosition
            selectedPosition = position
            notifyItemChanged(previous)
            notifyItemChanged(position)
            onCategoryClick(category)
        }
    }

    override fun getItemCount() = categories.size
}