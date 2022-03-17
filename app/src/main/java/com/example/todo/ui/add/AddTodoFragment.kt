package com.example.todo.ui.add

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.adapters.SpinnerAdapter
import com.example.todo.databinding.AddTodoFragmentBinding
import com.example.todo.ui.home.HomeUseCase

class AddTodoFragment : Fragment() {


    private lateinit var viewModel: AddTodoViewModel
    private lateinit var binding: AddTodoFragmentBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: List<String>
    private val safeArgs by navArgs<AddTodoFragmentArgs>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTodoFragmentBinding.inflate(layoutInflater)

        spLoad()

        spinnerAdapter = SpinnerAdapter(requireContext(),list)

        viewModel = ViewModelProvider(this).get(AddTodoViewModel::class.java)

        binding.todoStatus.adapter = spinnerAdapter

        binding.btnAdd.setOnClickListener {
            this.addTodoData()
            findNavController().popBackStack()
        }
        binding.tvTodoTimeAdd.text=HomeUseCase.LocalDateToParseTime(safeArgs.nowTime)

        binding.addTodoStartTime.setOnClickListener {
            viewModel.setStartTime(childFragmentManager,0)
        }


        binding.addTodoEndTime.setOnClickListener {
            viewModel.setEndTime(childFragmentManager,1)
        }

        viewModel._todoTime.observe(viewLifecycleOwner){
            binding.addTodoStartTime.text=it.startTime
            binding.addTodoEndTime.text=it.endTime
        }

        return binding.root
    }

    private fun addTodoData() {
        val title = binding.addTodoTitle.text.toString()
        val desc = binding.addTodoDesc.text.toString()
        val status = binding.todoStatus.selectedItemPosition
        val stTime = binding.addTodoStartTime.text.toString()
        val endTime = binding.addTodoEndTime.text.toString()
        viewModel.addTodo(title, desc, stTime, endTime, status, safeArgs.nowTime)
    }

    private fun spLoad() {
        list = arrayListOf("Easy", "Med...", "Hard")
    }
}