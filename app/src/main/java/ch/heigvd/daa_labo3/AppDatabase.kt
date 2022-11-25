package ch.heigvd.daa_labo3

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.daa_labo3.models.Note
import ch.heigvd.daa_labo3.models.Schedule
import ch.heigvd.daa_labo3.repositories.DataRepository
import ch.heigvd.daa_labo3.repositories.NoteDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Database(entities = [Note::class, Schedule::class], version = 1, exportSchema = true)
@TypeConverters(CalendarConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDAO
    companion object {
        private var INSTANCE : AppDatabase? = null
        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "mydatabase.db")
                    .fallbackToDestructiveMigration()
                    .addCallback(this.callback)
                    .build()
                INSTANCE!!
            }
        }

        private val callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    println("Populating database")
                    val isEmpty = database.noteDao().getCountNotes().value?.let { it == 0 } ?: true
                    println("How many notes? ${database.noteDao().getCountNotes().value}")
                    println("Database is empty: $isEmpty")
                    if (isEmpty) {
                        val repo = DataRepository(database.noteDao(), CoroutineScope(SupervisorJob()))
                        for (i in 0..10) {
                            println("Generating note $i")
                            repo.generateANote()
                        }
                    }
                }
            }
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                println("Database is open")
            }
        }
    }
}