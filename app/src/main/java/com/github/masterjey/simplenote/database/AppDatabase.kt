package com.github.masterjey.simplenote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.masterjey.simplenote.database.dao.NoteDao
import com.github.masterjey.simplenote.entity.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private var instance: AppDatabase? = null

        fun with(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "db")
                    .build()
            }
            return instance as AppDatabase
        }

    }

}