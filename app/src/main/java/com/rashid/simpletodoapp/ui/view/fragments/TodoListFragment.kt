package com.rashid.simpletodoapp.ui.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rashid.simpletodoapp.R
import com.rashid.simpletodoapp.data.model.TodoModel
import com.rashid.simpletodoapp.databinding.FragmentTodoListBinding
import com.rashid.simpletodoapp.ui.view.adapters.TodoAdapter
import com.rashid.simpletodoapp.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

class TodoListFragment : Fragment() {

    private lateinit var binding : FragmentTodoListBinding

    private val sharedVM : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedVM.getAllTodos(requireContext())
        clickListener()
        setupRV()
    }

    private fun setupRV() {
        binding.todoRv.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch{
            sharedVM.allTodos.collect {todolist ->
                binding.todoRv.adapter = TodoAdapter(todolist,{ todo ->
                    val bundle = Bundle()
                    bundle.putInt("todo_id",todo.id)
                    bundle.putString("todo_title",todo.title)
                    bundle.putString("todo_desc",todo.description)
                    findNavController()
                        .navigate(R.id.action_todoListFragment_to_addEditTodoFragment, bundle)
                },{todo ->
                    alertDialogToDelete(todo)
                })
                if(todolist.isEmpty()){
                    binding.todoRv.visibility = View.GONE
                }else{
                    binding.todoRv.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun alertDialogToDelete(todo: TodoModel) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Confirmation")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Delete") { dialog, _ ->
                sharedVM.deleteTodo(requireContext(),todo)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = builder.create()
        alertDialog.show()

    }

    private fun clickListener() {
        binding.addTodoFab.setOnClickListener {
            findNavController()
                .navigate(R.id.action_todoListFragment_to_addEditTodoFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        setupRV()
    }
}