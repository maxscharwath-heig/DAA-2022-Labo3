package ch.heigvd.daa_labo3

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.daa_labo3.viewmodels.NotesViewModel
import ch.heigvd.daa_labo3.viewmodels.NotesViewModelFactory
import ch.heigvd.daa_labo3.viewmodels.SortOrder

/**
 * Notes activity
 *
 * @author Nicolas Crausaz
 * @author Lazar Pavicevic
 * @author Maxime Scharwath
 */
class MainActivity : AppCompatActivity() {

    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.creation_date_filter -> {
                viewModel.setSortOrder(SortOrder.BY_CREATION_DATE)
                true
            }
            R.id.eta_filter -> {
                viewModel.setSortOrder(SortOrder.BY_ETA)
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