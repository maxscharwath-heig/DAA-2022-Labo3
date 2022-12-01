package ch.heigvd.daa_labo3.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.daa_labo3.repositories.DataRepository

class NotesViewModel(private val repository: DataRepository) : ViewModel() {

    var allNotes = repository.allNotes

    val countNotes = repository.countNotes

    fun sortByCreationDate() {
        repository.sortByCreationDate()
    }

    fun sortByETA() {
        repository.sortByETA()
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
