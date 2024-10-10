package com.rashid.simpletodoapp.ui.view.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rashid.simpletodoapp.R
import com.rashid.simpletodoapp.data.model.TodoModel
import com.rashid.simpletodoapp.databinding.FragmentAddEditTodoBinding
import com.rashid.simpletodoapp.viewmodel.SharedViewModel


class AddEditTodoFragment : Fragment() {

    private lateinit var binding: FragmentAddEditTodoBinding

    private val sharedVM: SharedViewModel by activityViewModels()

    var currentTodo: TodoModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        clickListener()
    }

    private fun setupUI() {
        val todoId = arguments?.getInt("todo_id") ?: return
        val todoTitle = arguments?.getString("todo_title") ?: return
        val todoDesc = arguments?.getString("todo_desc") ?: return
        binding.addItemTv.text = getString(R.string.edit_todo_item)
        binding.saveTodo.text = getString(R.string.update)

        currentTodo = TodoModel(todoId, todoTitle, todoDesc)
        binding.todoTitleEt.editText?.text = currentTodo?.title?.toEditable()
        binding.todoDescEt.editText?.text = currentTodo?.description?.toEditable()
    }


    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun clickListener() {
        binding.saveTodo.setOnClickListener {
            saveTodo()
        }
    }

    private fun saveTodo() {
        val title = binding.todoTitleEt.editText?.text.toString().trim()
        val desc = binding.todoDescEt.editText?.text.toString().trim()
        if (title.isEmpty() || title.isBlank()) {
            Toast.makeText(requireContext(), "Enter title", Toast.LENGTH_SHORT).show()
            return
        }
        if (desc.isEmpty() || desc.isBlank()) {
            Toast.makeText(requireContext(), "Enter description", Toast.LENGTH_SHORT).show()
            return
        }
        val todo = TodoModel(id = currentTodo?.id ?: 0, title = title, description = desc)
        sharedVM.upsertTodo(requireContext(), todo)
        findNavController().popBackStack()
    }
}