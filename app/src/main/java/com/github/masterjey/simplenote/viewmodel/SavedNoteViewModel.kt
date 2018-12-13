package com.github.masterjey.simplenote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.masterjey.simplenote.database.AppDatabase
import com.github.masterjey.simplenote.entity.Note
import java.util.concurrent.Executors

class SavedNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = AppDatabase.with(application.baseContext).noteDao()
    private val executorService = Executors.newSingleThreadExecutor()

    fun getAllNotes(): LiveData<MutableList<Note>> {
        return noteDao.getAllNotesLiveData()
    }

    fun deleteNote(note: Note) {
        executorService.execute { noteDao.delete(note) }
    }

    fun deleteAll() {
        executorService.execute { noteDao.clearTable() }
    }

}