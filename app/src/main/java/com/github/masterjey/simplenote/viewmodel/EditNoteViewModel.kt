package com.github.masterjey.simplenote.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.github.masterjey.simplenote.database.AppDatabase
import com.github.masterjey.simplenote.entity.Note
import java.util.concurrent.Executors

class EditNoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = AppDatabase.with(application.baseContext).noteDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun saveNote(note: Note) {
        executor.execute { noteDao.update(note) }
    }

}