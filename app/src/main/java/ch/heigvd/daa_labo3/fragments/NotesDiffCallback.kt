package ch.heigvd.daa_labo3.fragments

import androidx.recyclerview.widget.DiffUtil
import ch.heigvd.daa_labo3.models.Note

class NotesDiffCallback(private val oldList: List<Note>, private val newList: List<Note>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].noteId == newList[newItemPosition].noteId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old::class == new::class && old.state == new.state
    }
}