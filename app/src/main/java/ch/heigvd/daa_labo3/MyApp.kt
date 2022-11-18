package ch.heigvd.daa_labo3

import android.app.Application
import ch.heigvd.daa_labo3.repositories.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val repository by lazy {
        val database = AppDatabase.getDatabase(this)
        DataRepository(database.noteDao(), applicationScope)
    }
}