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
import kotlin.concurrent.thread

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
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { database ->
                                val isEmpty = database.noteDao().getCountNotes().value == 0
                                if (isEmpty) {
                                    val repo = DataRepository(database.noteDao(), CoroutineScope(SupervisorJob()))
                                    for (i in 0..10) {
                                        repo.generateANote()
                                    }
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE!!
            }
        }
    }
}