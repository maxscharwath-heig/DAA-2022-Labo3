package ch.heigvd.daa_labo3.repositories

import androidx.lifecycle.LiveData
import ch.heigvd.daa_labo3.models.Note
import ch.heigvd.daa_labo3.models.NoteAndSchedule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Data unique access point to the database through DAO
 *
 * @author Nicolas Crausaz
 * @author Lazar Pavicevic
 * @author Maxime Scharwath
 */
class DataRepository(private val noteDAO: NoteDAO, private val applicationScope: CoroutineScope) {
    var allNotes: LiveData<List<NoteAndSchedule>> = noteDAO.getAllNotes()
    val countNotes: LiveData<Int> = noteDAO.getCountNotes()

    fun deleteAllNotes() {
        applicationScope.launch {
            noteDAO.deleteAllNotes()
        }
    }

    fun generateANote() {
        applicationScope.launch {
            val note = Note.generateRandomNote()
            val schedule = Note.generateRandomSchedule()

            val id = noteDAO.insert(note)
            if (schedule != null) {
                schedule.ownerId = id
                noteDAO.insert(schedule)
            }
        }
    }
}