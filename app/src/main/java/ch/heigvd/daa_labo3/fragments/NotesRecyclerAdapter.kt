package ch.heigvd.daa_labo3.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.daa_labo3.R
import ch.heigvd.daa_labo3.models.NoteAndSchedule
import ch.heigvd.daa_labo3.models.State.*
import ch.heigvd.daa_labo3.models.Type.*


class NotesRecyclerAdapter(_items: List<NoteAndSchedule> = listOf()) :
    RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iconNote = view.findViewById<ImageView>(R.id.list_item_icon)
        private val titleNote = view.findViewById<TextView>(R.id.list_item_title)
        private val textNote = view.findViewById<TextView>(R.id.list_item_text)
        fun bind(noteAndSchedule: NoteAndSchedule) {
            iconNote.setImageResource(
                when (noteAndSchedule.note.type) {
                    TODO -> R.drawable.todo
                    SHOPPING -> R.drawable.shopping
                    WORK -> R.drawable.work
                    FAMILY -> R.drawable.family
                    else -> R.drawable.note
                }
            )
            iconNote.imageTintList = when (noteAndSchedule.note.state) {
                IN_PROGRESS -> ContextCompat.getColorStateList(iconNote.context, R.color.grey)
                DONE -> ContextCompat.getColorStateList(iconNote.context, R.color.green)
            }
            titleNote?.text = noteAndSchedule.note.title
            textNote?.text = noteAndSchedule.note.text
        }
    }

    var items = listOf<NoteAndSchedule>()

    set(value) {
        val diffCallback = NotesDiffCallback(items, value)
        val diffItems = DiffUtil.calculateDiff(diffCallback)
        field = value
        diffItems.dispatchUpdatesTo(this)
    }

    init {
        items = _items
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].note.type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_notes, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}