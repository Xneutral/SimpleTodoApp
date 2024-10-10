package com.rashid.simpletodoapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.rashid.simpletodoapp.data.model.TodoModel
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Upsert
    suspend fun upsertTodo(todo: TodoModel)

    @Delete
    suspend fun deleteTodo(todo: TodoModel)

    @Query("SELECT * FROM todo_table")
    fun getAllTodos() : Flow<List<TodoModel>>

    @Query("SELECT * FROM todo_table WHERE id=:id")
    suspend fun getTodoById(id : Int) : TodoModel

}