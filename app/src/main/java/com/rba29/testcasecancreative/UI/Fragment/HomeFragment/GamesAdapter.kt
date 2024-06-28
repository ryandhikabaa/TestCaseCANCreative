package com.rba29.testcasecancreative.UI.Fragment.HomeFragment

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rba29.testcasecancreative.Data.Response.ListResultItem
import com.rba29.testcasecancreative.databinding.ItemRowGameBinding

class GamesAdapter : PagingDataAdapter<ListResultItem, GamesAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemRowGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
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


                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, StoryDetailActivity::class.java)
//                    intent.putExtra("EXTRA_STORIES", story)
//
//
//                    val optionsCompat: ActivityOptionsCompat =
//                        ActivityOptionsCompat.makeSceneTransitionAnimation(
//                            itemView.context as Activity,
//                            Pair(ivStory, "image"),
//                            Pair(tvName, "name"),
//                            Pair(tvDesc, "description"),
//                            Pair(createdAt, "createdAt"),
//                        )
//                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListResultItem>() {
            override fun areItemsTheSame(oldItem: ListResultItem, newItem: ListResultItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListResultItem, newItem: ListResultItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}