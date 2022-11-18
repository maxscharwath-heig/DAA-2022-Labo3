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
    public abstract fun noteDao() : NoteDAO
    companion object {
        private var INSTANCE : AppDatabase? = null
        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE!!
            }
        }
    }

    fun getDatabase(context: Context) : AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "mydatabase.db")
                .fallbackToDestructiveMigration()
                .addCallback(myDatabaseCallBack())
                .build()
            INSTANCE!!
        }
    }

    private fun myDatabaseCallBack(): Callback {
        return object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    val isEmpty = database.noteDao().getCountNotes().value == 0
                    if (isEmpty) {
                        thread {
                            for (i in 1..10) {
                                database.noteDao().insert(Note.generateRandomNote())
                            }
                        }
                    }
                }
            }
        }
    }
}