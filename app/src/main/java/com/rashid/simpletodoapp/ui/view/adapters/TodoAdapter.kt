package com.rashid.simpletodoapp.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rashid.simpletodoapp.data.model.TodoModel
import com.rashid.simpletodoapp.databinding.TodoItemBinding

class TodoAdapter(
    private val todoList: List<TodoModel>,
    private val onEdit: (todo: TodoModel) -> Unit,
    private val onDelete: (todo: TodoModel) -> Unit,
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TodoItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = todoList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList[position]

        holder.binding.todoTitleTv.text = todo.title
        holder.binding.todoDescTv.text = todo.description

        holder.binding.editTodo.setOnClickListener {
            onEdit(todo)
        }

        holder.binding.deleteTodo.setOnClickListener {
            onDelete(todo)
        }
    }


}