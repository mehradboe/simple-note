package com.github.masterjey.simplenote.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.github.masterjey.simplenote.database.AppDatabase
import com.github.masterjey.simplenote.entity.Note
import java.util.concurrent.Executors

class AddNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = AppDatabase.with(application.baseContext).noteDao()

    private val executor = Executors.newSingleThreadExecutor()

    fun addNewNote(note: Note) {
        executor.execute {
            noteDao.insert(note)
        }
    }

}