package ch.heigvd.daa_labo3.fragments

import android.content.Context
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
import java.util.Calendar

/**
 * Adapter for displaying notes
 *
 * @author Nicolas Crausaz
 * @author Lazar Pavicevic
 * @author Maxime Scharwath
 */
class NotesRecyclerAdapter(_items: List<NoteAndSchedule> = listOf()) :
    RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val iconNote = view.findViewById<ImageView>(R.id.list_item_icon)
        private val titleNote = view.findViewById<TextView>(R.id.list_item_title)
        private val textNote = view.findViewById<TextView>(R.id.list_item_text)

        private val iconSchedule = view.findViewById<ImageView>(R.id.schedule_icon)
        private val textSchedule = view.findViewById<TextView>(R.id.schedule_text)

        fun bind(noteAndSchedule: NoteAndSchedule) {
            iconNote.setImageResource(
                when (noteAndSchedule.note.type) {
                    TODO -> R.drawable.todo
                    SHOPPING -> R.drawable.shopping
                    WORK -> R.drawable.work
                    FAMILY -> R.drawable.family
                    NONE -> R.drawable.note
                }
            )
            iconNote.imageTintList = when (noteAndSchedule.note.state) {
                IN_PROGRESS -> ContextCompat.getColorStateList(iconNote.context, R.color.grey)
                DONE -> ContextCompat.getColorStateList(iconNote.context, R.color.green)
            }
            titleNote?.text = noteAndSchedule.note.title
            textNote?.text = noteAndSchedule.note.text
            if (noteAndSchedule.schedule != null) {
                iconSchedule.visibility = View.VISIBLE
                textSchedule.visibility = View.VISIBLE

                val (dateText, isLate) = displayDateDifference(
                    itemView.context,
                    noteAndSchedule.note.creationDate,
                    noteAndSchedule.schedule.date
                )

                textSchedule.text = dateText
                iconSchedule.imageTintList = when (isLate) {
                    true -> ContextCompat.getColorStateList(iconSchedule.context, R.color.red)
                    false -> ContextCompat.getColorStateList(iconSchedule.context, R.color.grey)
                }
            }
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

    private fun displayDateDifference(
        context: Context,
        creationDate: Calendar,
        dueDate: Calendar
    ): Pair<String, Boolean> {
        val late = false
        val dateText: String
        val diff = dueDate.timeInMillis.minus(creationDate.timeInMillis)

        if (diff < 0) {
            return Pair(context.getString(R.string.schedule_late), true)
        }

        val diffMonths = diff.div(1000 * 60 * 60 * 24).div(30)
        val diffWeeks = diff.div(1000 * 60 * 60 * 24 * 7)
        val diffDays = diff.div(1000 * 60 * 60 * 24)
        val diffHours = diff.div(1000 * 60 * 60) % 24
        val diffMinutes = diff.div(1000 * 60) % 60

        dateText = when {
            diffMonths > 0 -> context.resources.getQuantityString(
                R.plurals.schedule_month,
                diffMonths.toInt(),
                diffMonths
            )
            diffWeeks > 0 -> context.resources.getQuantityString(
                R.plurals.schedule_week,
                diffWeeks.toInt(),
                diffWeeks
            )
            diffDays > 0 -> context.resources.getQuantityString(
                R.plurals.schedule_day,
                diffDays.toInt(),
                diffDays
            )
            diffHours > 0 -> context.resources.getQuantityString(
                R.plurals.schedule_hour,
                diffHours.toInt(),
                diffHours
            )
            else -> context.resources.getQuantityString(
                R.plurals.schedule_minute,
                diffMinutes.toInt(),
                diffMinutes
            )
        }

        return Pair(dateText, late)
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