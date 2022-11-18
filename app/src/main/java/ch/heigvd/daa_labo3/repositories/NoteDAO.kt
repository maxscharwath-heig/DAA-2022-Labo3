package ch.heigvd.daa_labo3.repositories

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.heigvd.daa_labo3.models.Note
import ch.heigvd.daa_labo3.models.NoteAndSchedule
import ch.heigvd.daa_labo3.models.Schedule

@Dao
interface NoteDAO {
    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    @Query("DELETE FROM note")
    fun deleteAllNotes()

    @Query("SELECT COUNT(*) FROM note")
    fun getCountNotes(): LiveData<Int>

    //@Insert
    //fun insertAll(vararg notes: NoteAndSchedule)

    @Insert
    fun insert(note: Note): Long

    @Insert
    fun insert(schedule: Schedule): Long
}