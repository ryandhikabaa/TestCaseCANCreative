package com.rba29.testcasecancreative.UI.Fragment.FavoriteFragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rba29.testcasecancreative.Data.Db.FavoriteGames.FavoriteGames
import com.rba29.testcasecancreative.databinding.ItemRowGameBinding

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    private val listFavoriteGames = ArrayList<FavoriteGames>()

    fun setListFavoriteGames(listFavoriteGames: List<FavoriteGames>) {
        val diffCallback = FavoriteDiffCallback(this.listFavoriteGames, listFavoriteGames)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteGames.clear()
        this.listFavoriteGames.addAll(listFavoriteGames)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemRowGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavoriteGames.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavoriteGames[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavoriteGames[position])
        }
    }

    class FavoriteViewHolder(private val binding: ItemRowGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: FavoriteGames) {
            with(binding){
                tvTitle.text = result.name
                var sRilisDate = result.released
                tvRilisDate.text = "Realese Date : $sRilisDate"
                tvRate.text = result.rating
                Glide.with(root)
                    .load(result.background_image)
                    .into(ivGames)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: FavoriteGames)
    }

}