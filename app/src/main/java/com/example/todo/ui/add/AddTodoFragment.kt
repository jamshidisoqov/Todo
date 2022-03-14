package com.example.todo.ui.add

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.text.TextWatcher
import android.text.format.DateFormat.is24HourFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.adapters.SpinnerAdapter
import com.example.todo.models.TodoModel
import com.example.todo.databinding.AddTodoFragmentBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class AddTodoFragment : Fragment() {


    private lateinit var viewModel: AddTodoViewModel
    private lateinit var binding: AddTodoFragmentBinding
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: List<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        spLoad()

        spinnerAdapter=SpinnerAdapter(requireContext(),list)

        viewModel = ViewModelProvider(this).get(AddTodoViewModel::class.java)

        binding = AddTodoFragmentBinding.inflate(layoutInflater)

        binding.todoStatus.adapter=spinnerAdapter

        binding.btnAdd.setOnClickListener {
            this.addTodoData()
            findNavController().navigate(R.id.action_addTodoFragment_to_homeFragment)
        }

        binding.addTodoStartTime.setOnClickListener {
            val hour = openTimerPicker()
            binding.addTodoStartTime.text=hour
        }

        return binding.root
    }

     fun addTodoData() {
        var title=binding.addTodoTitle.text.toString()
        var desc=binding.addTodoDesc.text.toString()
        var status=binding.todoStatus.selectedItemPosition
         Toast.makeText(context, "status=$status", Toast.LENGTH_SHORT).show()
        var stTime=binding.addTodoStartTime.text.toString()
        var endTime=binding.addTodoEndTime.text.toString()
        if(checkTodo(title,desc,stTime,endTime)){

            val todo= TodoModel(0,title,desc,status+1,stTime,endTime,
                "aas",false,false)
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                viewModel.addTodo(todo)
            }
        }
    }

    private fun checkTodo(title: String, desc: String, stTime: String, endTime: String): Boolean {
        return !TextUtils.isEmpty(title)&&!TextUtils.isEmpty(desc)&&!TextUtils.isEmpty(stTime)&&!TextUtils.isEmpty(endTime)
    }



    private fun spLoad(){
        list= arrayListOf("Easy","Med...","Hard")
    }

    fun openTimerPicker():String{
        val timerPICKER=is24HourFormat(requireContext())
        val clockFormat=if (timerPICKER) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker=MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set Alarm")
            .build()
        picker.show(childFragmentManager,"Tag")

        picker.addOnPositiveButtonClickListener {
            binding.addTodoStartTime.text= "${picker.hour}:${picker.minute}"
        }

        return "12:00"


    }



}