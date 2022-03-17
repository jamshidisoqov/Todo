package com.example.todo.ui.update

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.adapters.SpinnerAdapter
import com.example.todo.databinding.UpdateFragmentBinding
import com.example.todo.models.TodoModel
import com.example.todo.ui.home.HomeUseCase

class UpdateFragment : Fragment() {

    private val safeVarargs by navArgs<UpdateFragmentArgs>()


    private lateinit var viewModel: UpdateViewModel
    private lateinit var binding: UpdateFragmentBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: List<String>


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UpdateFragmentBinding.inflate(layoutInflater)

        spLoad()

        spinnerAdapter = SpinnerAdapter(requireContext(), list)

        binding.todoStatus.adapter = spinnerAdapter

        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)


        binding.apply {
            val current = safeVarargs.currentItem
            this.updateTodoTitle.setText(current.title)
            this.updateTodoDesc.setText(current.desc)
            this.updateTodoStartTime.setText(current.start)
            this.updateTodoEndTime.setText(current.end)
            this.todoStatus.setSelection(current.status)
            val format=HomeUseCase.parseToLocalDate(safeVarargs.currentItem.time)
            this.tvTodoTime.text="${format.dayOfWeek.name.substring(0,3)},${format.dayOfMonth} ${format.month.name}"
        }

        binding.btnUpdate.setOnClickListener {
            viewModel.updateUser(
                TodoModel(
                    safeVarargs.currentItem.id,
                    binding.updateTodoTitle.text.toString(),
                    binding.updateTodoDesc.text.toString(),
                    binding.todoStatus.selectedItemPosition,
                    binding.updateTodoStartTime.text.toString(),
                    binding.updateTodoEndTime.text.toString(),
                    safeVarargs.currentItem.time,
                    safeVarargs.currentItem.todo,
                    false
                )
            )
            findNavController().popBackStack()
        }
        return binding.root
    }


    private fun spLoad() {
        list = arrayListOf("Easy", "Med...", "Hard")
    }



}