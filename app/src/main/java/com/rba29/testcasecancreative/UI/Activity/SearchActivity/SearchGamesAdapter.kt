package com.rba29.testcasecancreative.UI.Activity.SearchActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import com.rba29.testcasecancreative.databinding.ItemRowGameBinding

class SearchGamesAdapter :
    ListAdapter<ListResultItem, SearchGamesAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(review)
        }

    }

    class MyViewHolder(val binding: ItemRowGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListResultItem) {
            with(binding) {
                tvTitle.text = story.name
                var sRilisDate = story.released
                tvRilisDate.text = "Realese Date : $sRilisDate"
                tvRate.text = story.rating
                Glide.with(root)
                    .load(story.background_image)
                    .into(ivGames)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListResultItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListResultItem>() {
            override fun areItemsTheSame(oldItem: ListResultItem, newItem: ListResultItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListResultItem, newItem: ListResultItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}