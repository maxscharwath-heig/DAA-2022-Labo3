package ch.heigvd.daa_labo3

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import ch.heigvd.daa_labo3.models.Note
import ch.heigvd.daa_labo3.models.Schedule
import ch.heigvd.daa_labo3.repositories.NoteDAO
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
                    .addCallback(this.callback)
                    .build()
                INSTANCE!!
            }
        }

        private val callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    thread {
                        for (i in 0..10) {
                            val note = Note.generateRandomNote()
                            val schedule = Note.generateRandomSchedule()

                            val id = database.noteDao().insert(note)
                            if (schedule != null) {
                                schedule.ownerId = id
                                database.noteDao().insert(schedule)
                            }
                        }
                    }
                }
            }
        }
    }
}