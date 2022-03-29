package com.stambulo.redditinfinitylist.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stambulo.redditinfinitylist.databinding.ItemPostBinding
import com.stambulo.redditinfinitylist.model.entity.Children
import com.stambulo.redditinfinitylist.model.entity.DataX

class RedditAdapter : RecyclerView.Adapter<RedditAdapter.ViewHolder>() {

    private var success: List<Children>

    init {
        success = arrayListOf(
            Children(DataX(0, 0,0, "Class Initializer"),"kind : String")
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        success.let { holder.bind(position, it) }
    }

    override fun getItemCount(): Int {
        return success.size
    }

    class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int, success: List<Children>) {
            binding.tvTitle.text = success[position].data.title
            binding.tvRate.text = success[position].data.score.toString()
            binding.tvComments.text = success[position].data.num_comments.toString()
        }
    }

    fun setData(data: List<Children>) {
        success = data
    }
}
