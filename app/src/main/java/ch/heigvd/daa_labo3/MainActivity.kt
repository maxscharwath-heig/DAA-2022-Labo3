package ch.heigvd.daa_labo3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Chargement de l'activité principale")
        println((application as MyApp).repository.allNotes.value)
        (application as MyApp).repository.allNotes.observe(this) { notes ->
            println("Notes reçues: $notes")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.creation_date_filter -> {
                /* TODO: filter by creation date */
                true
            }
            R.id.eta_filter -> {
                /* TODO: filter by eta */
                true
            }
            R.id.menu_actions_generate -> {
                /* TODO: generate data */
                true
            }
            R.id.menu_actions_delete_all -> {
                /* TODO: delete all */
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}