package com.rashid.simpletodoapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashid.simpletodoapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.rashid.simpletodoapp.data.model.TodoModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow


class SharedViewModel : ViewModel() {

    private val todoRepository = TodoRepository()

    // StateFlow to hold the list of Todos (initially an empty list)
    private val _allTodos = MutableStateFlow<List<TodoModel>>(emptyList())
    val allTodos: StateFlow<List<TodoModel>> = _allTodos.asStateFlow()


    fun upsertTodo(context : Context, todo: TodoModel) {
        viewModelScope.launch {
            todoRepository.upsertTodo(context,todo)
            getAllTodos(context)
        }
    }

    fun deleteTodo(context: Context,todo: TodoModel) {
        viewModelScope.launch {
            todoRepository.deleteTodo(context,todo)
            getAllTodos(context)
        }
    }

    fun getAllTodos(context: Context){
        viewModelScope.launch {
            todoRepository.getAllTodos(context).collect { todos ->
                _allTodos.value = todos
            }
        }
    }
}
