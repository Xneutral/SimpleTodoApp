package com.rashid.simpletodoapp.data.repository

import android.content.Context
import com.rashid.simpletodoapp.data.db.TodoDao
import com.rashid.simpletodoapp.data.db.TodoDatabase
import com.rashid.simpletodoapp.data.model.TodoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TodoRepository() {


    suspend fun upsertTodo(context: Context, todo: TodoModel) {
        val todoDao = TodoDatabase.getDatabase(context).todoDao()
        todoDao.upsertTodo(todo)
    }

    suspend fun deleteTodo(context: Context, todo: TodoModel) {
        val todoDao = TodoDatabase.getDatabase(context).todoDao()
        todoDao.deleteTodo(todo)
    }

    fun getAllTodos(context: Context): Flow<List<TodoModel>> {
        val todoDao = TodoDatabase.getDatabase(context).todoDao()
        return todoDao.getAllTodos()
    }

    suspend fun getTodoById(context: Context, id: Int): TodoModel {
        val todoDao = TodoDatabase.getDatabase(context).todoDao()
        return todoDao.getTodoById(id)
    }
}
