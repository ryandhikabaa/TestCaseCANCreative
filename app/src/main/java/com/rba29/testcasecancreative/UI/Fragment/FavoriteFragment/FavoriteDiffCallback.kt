package com.rba29.testcasecancreative.UI.Fragment.FavoriteFragment

import androidx.recyclerview.widget.DiffUtil
import com.rba29.testcasecancreative.Data.Db.FavoriteGames.FavoriteGames

class FavoriteDiffCallback (private val oldNoteList: List<FavoriteGames>, private val newNoteList: List<FavoriteGames>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldNoteList.size
    override fun getNewListSize(): Int = newNoteList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].name == newNoteList[newItemPosition].name
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNote = oldNoteList[oldItemPosition]
        val newNote = newNoteList[newItemPosition]
        return oldNote.id == newNote.id && oldNote.name == newNote.name && oldNote.released == newNote.released && oldNote.slug == newNote.slug && oldNote.rating == newNote.rating && oldNote.background_image == newNote.background_image
    }
}