package com.rashid.simpletodoapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rashid.simpletodoapp.data.model.TodoModel


@Database(entities = [TodoModel::class], exportSchema = false, version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    
    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(
            context: Context
        ): TodoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}