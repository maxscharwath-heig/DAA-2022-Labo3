package ch.heigvd.daa_labo3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.daa_labo3.repositories.DataRepository

enum class SortOrder { BY_CREATION_DATE, BY_ETA, BY_NONE }

/**
 * ViewModel used to interact with data in the activities & fragments scopes.
 *
 * @author Nicolas Crausaz
 * @author Lazar Pavicevic
 * @author Maxime Scharwath
 */
class NotesViewModel(private val repository: DataRepository) : ViewModel() {

    var allNotes = repository.allNotes

    val countNotes = repository.countNotes

    private val sortOrder: LiveData<SortOrder> = MutableLiveData(SortOrder.BY_NONE)

    private val sortedNotesByDate = Transformations.map(allNotes) { notes ->
        notes.sortedByDescending { note -> note.note.creationDate }
    }

    private val sortedNotesByEta = Transformations.map(allNotes) { notes ->
        notes.sortedWith(compareBy(nullsLast()) { note -> note.schedule?.date })
    }

    val sortedNotes = Transformations.switchMap(sortOrder) {
        when (it) {
            SortOrder.BY_CREATION_DATE -> sortedNotesByDate
            SortOrder.BY_ETA -> sortedNotesByEta
            else -> allNotes
        }
    }

    fun setSortOrder(sortOrder: SortOrder) {
        (this.sortOrder as MutableLiveData).value = sortOrder
    }

    fun generateANote() {
        repository.generateANote()
    }

    fun deleteAllNote() {
        repository.deleteAllNotes()
    }
}

@Suppress("UNCHECKED_CAST")
class NotesViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
