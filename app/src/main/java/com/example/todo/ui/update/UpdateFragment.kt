package com.example.todo.ui.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.adapters.SpinnerAdapter
import com.example.todo.data.CurrentTodo
import com.example.todo.databinding.UpdateFragmentBinding
import com.example.todo.models.TodoModel

class UpdateFragment : Fragment() {

    private val safeVarargs by navArgs<UpdateFragmentArgs>()



    private lateinit var viewModel: UpdateViewModel
    private lateinit var binding: UpdateFragmentBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: List<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UpdateFragmentBinding.inflate(layoutInflater)

        spLoad()

        spinnerAdapter=SpinnerAdapter(requireContext(),list)

        binding.todoStatus.adapter=spinnerAdapter

        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)
        if (safeVarargs.currentItem!=null)
        viewModel.todoModel!!.value=safeVarargs.currentItem

        binding.apply {
            viewModel.todoModel!!.observe(viewLifecycleOwner){
                this.updateTodoTitle.setText(it.title)
                this.updateTodoDesc.setText(it.desc)
                this.updateTodoStartTime.setText(it.start)
                this.updateTodoEndTime.setText(it.end)
                this.todoStatus.setSelection(it.status)
            }
        }


        return binding.root
    }



    private fun spLoad(){
        list= arrayListOf("Easy","Med...","Hard")
    }

}