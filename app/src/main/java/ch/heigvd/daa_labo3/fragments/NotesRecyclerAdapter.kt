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
import java.util.concurrent.TimeUnit

/**
 * Adapter for displaying notes
 *
 * @author Nicolas Crausaz
 * @author Lazar Pavicevic
 * @author Maxime Scharwath
 */
class NotesRecyclerAdapter(_items: List<NoteAndSchedule> = listOf()) :
    RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>() {

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
        return if (items[position].schedule != null) NOTE_SCHEDULE else NOTE
    }

    companion object {
        private const val NOTE = 0
        private const val NOTE_SCHEDULE = 1
    }

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
            titleNote.text = noteAndSchedule.note.title
            textNote.text = noteAndSchedule.note.text

            if (noteAndSchedule.schedule != null) {
                val (dateText, isLate) = displayDateDifference(
                    itemView.context,
                    noteAndSchedule.schedule.date
                )

                textSchedule.text = dateText
                iconSchedule.imageTintList = when (isLate) {
                    true -> ContextCompat.getColorStateList(iconSchedule.context, R.color.red)
                    false -> ContextCompat.getColorStateList(iconSchedule.context, R.color.grey)
                }
            }
        }

        private fun displayDateDifference(context: Context, dueDate: Calendar): Pair<String, Boolean> {
            val today = Calendar.getInstance()
            val diff = dueDate.timeInMillis.minus(today.timeInMillis)

            if (diff <= 0) return Pair(context.getString(R.string.schedule_late), true)

            val diffMinutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
            val diffHours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)
            val diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            val diffWeeks = diffDays.div(today.getActualMaximum(Calendar.DAY_OF_WEEK))
            val diffMonths = diffDays.div(today.getActualMaximum(Calendar.DAY_OF_MONTH))

            when {
                diffMonths > 0 ->
                    return Pair(
                        context.resources.getQuantityString(
                            R.plurals.schedule_month,
                            diffMonths.toInt(),
                            diffMonths
                        ), false
                    )
                diffWeeks > 0 ->
                    return Pair(
                        context.resources.getQuantityString(
                            R.plurals.schedule_week,
                            diffWeeks.toInt(),
                            diffWeeks
                        ), false
                    )
                diffDays > 0 ->
                    return Pair(
                        context.resources.getQuantityString(
                            R.plurals.schedule_day,
                            diffDays.toInt(),
                            diffDays
                        ), false
                    )
                diffHours > 0 ->
                    return Pair(
                        context.resources.getQuantityString(
                            R.plurals.schedule_hour,
                            diffHours.toInt(),
                            diffHours
                        ), false
                    )
                diffMinutes > 0 ->
                    return Pair(
                        context.resources.getQuantityString(
                            R.plurals.schedule_minute,
                            diffMinutes.toInt(),
                            diffMinutes
                        ), false
                    )
                else -> return Pair(
                    context.getString(R.string.schedule_late),
                    true
                ) // We assume if the remaining time is less than a minute, it's late
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            NOTE -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_note, parent, false)
            )
            else -> ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_note_schedule, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}