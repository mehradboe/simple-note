package com.github.masterjey.simplenote.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.github.masterjey.simplenote.entity.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE note.id = :id")
    fun getNoteById(id: Int): Note

    @Query("SELECT * FROM note")
    fun getAllNotes(): MutableList<Note>

    @Query("SELECT * FROM note ORDER BY editedDate DESC")
    fun getAllNotesLiveData(): LiveData<MutableList<Note>>

    @Insert
    fun insert(vararg note: Note)

    @Update
    fun update(vararg note: Note)

    @Delete
    fun delete(vararg note: Note)

    @Query("DELETE FROM note")
    fun clearTable()

}