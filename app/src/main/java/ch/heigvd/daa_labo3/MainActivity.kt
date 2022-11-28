package ch.heigvd.daa_labo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import ch.heigvd.daa_labo3.viewmodels.NotesViewModel
import ch.heigvd.daa_labo3.viewmodels.NotesViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewModel: NotesViewModel by viewModels<NotesViewModel> {
        NotesViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
/*
        println("Chargement de l'activité principale")
        println((application as MyApp).repository.allNotes.value)
        (application as MyApp).repository.allNotes.observe(this) { notes ->
            println("Notes reçues: $notes")
        }

 */
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.creation_date_filter -> {
                viewModel.sortByCreationDate()
                true
            }
            R.id.eta_filter -> {
                viewModel.sortByETA()
                true
            }
            R.id.menu_actions_generate -> {
                viewModel.generateANote()
                true
            }
            R.id.menu_actions_delete_all -> {
                viewModel.deleteAllNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}