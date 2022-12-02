package ch.heigvd.daa_labo3.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.daa_labo3.models.NoteAndSchedule
import ch.heigvd.daa_labo3.repositories.DataRepository

enum class SortOrder { BY_CREATION_DATE, BY_ETA, BY_NONE }

class NotesViewModel(private val repository: DataRepository) : ViewModel() {

    var allNotes = repository.allNotes

    val countNotes = repository.countNotes

    private val sortOrder: LiveData<SortOrder> = MutableLiveData(SortOrder.BY_CREATION_DATE)

    fun setSortOrder(sortOrder: SortOrder) {
        (this.sortOrder as MutableLiveData).value = sortOrder
    }

    fun getFilteredNotes(): LiveData<List<NoteAndSchedule>> {
        return Transformations.map(repository.allNotes) { notes ->
            when (sortOrder.value) {
                SortOrder.BY_CREATION_DATE -> notes.sortedByDescending { it.note.creationDate }
                SortOrder.BY_ETA -> notes.sortedBy { it.schedule?.date }
                else -> {
                    notes
                }
            }
        }
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
