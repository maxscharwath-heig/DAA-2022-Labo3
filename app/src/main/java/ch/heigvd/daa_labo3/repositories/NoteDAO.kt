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

    @Query("SELECT * FROM note ORDER BY creationDate DESC")
    fun getAllNotesSortedByCreationDate(): LiveData<List<NoteAndSchedule>>

    //@Query("SELECT * FROM note n INNER JOIN schedule s ON n.noteId = s.ownerId ORDER BY s.date DESC")
    @Query("SELECT * FROM note ORDER BY creationDate ASC")
    fun getAllNotesSortedByETA(): LiveData<List<NoteAndSchedule>>

    @Insert
    fun insert(note: Note): Long

    @Insert
    fun insert(schedule: Schedule): Long
}