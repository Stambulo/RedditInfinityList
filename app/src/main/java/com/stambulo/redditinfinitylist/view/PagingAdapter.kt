package com.stambulo.redditinfinitylist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.stambulo.redditinfinitylist.databinding.ItemPostBinding
import com.stambulo.redditinfinitylist.model.entity.DataX

class PagingAdapter: PagingDataAdapter<DataX, PagingAdapter.ViewHolder>(DataDiffCallback) {

    private lateinit var bindingItem: ItemPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingAdapter.ViewHolder {
        bindingItem = ItemPostBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(bindingItem)
    }

    override fun onBindViewHolder(holder: PagingAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: DataX?){
            if (layoutPosition != RecyclerView.NO_POSITION){
                data?.let {
                    with(binding){
                        tvTitle.text = data.title
                        tvRate.text = data.score.toString()
                        tvComments.text = data.num_comments.toString()
                    }
                }
            }
        }
    }

    private object DataDiffCallback : DiffUtil.ItemCallback<DataX>() {
        override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
            return oldItem.title == newItem.title && oldItem.score == newItem.score
                    && oldItem.num_comments == newItem.num_comments
        }
    }
}
