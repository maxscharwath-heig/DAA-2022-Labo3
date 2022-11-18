package ch.heigvd.daa_labo3.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.daa_labo3.R
import ch.heigvd.daa_labo3.models.Note
import ch.heigvd.daa_labo3.models.Type.*


class ControlsRecyclerAdapter(_items : List<Note> = listOf()) : RecyclerView.Adapter<ControlsRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleNote = view.findViewById<TextView>(R.id.list_item_title)
        private val textNote = view.findViewById<TextView>(R.id.list_item_text)
        fun bind(note : Note) {
            titleNote?.text = note.title
            textNote?.text = note.text
        }
    }

    var items = listOf<Note>()

    set(value) {
        field = value
        notifyDataSetChanged()
    }
    init { items = _items }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            TODO.ordinal -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_notes, parent, false))
            SHOPPING.ordinal -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_notes, parent, false))
            WORK.ordinal -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_notes, parent, false))
            FAMILY.ordinal -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_notes, parent, false))
            else -> /* NONE */ ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_notes, parent, false))
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}