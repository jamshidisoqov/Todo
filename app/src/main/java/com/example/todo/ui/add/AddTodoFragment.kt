package com.example.todo.ui.add

import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo.ui.home.adapters.SpinnerAdapter
import com.example.todo.databinding.AddTodoFragmentBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

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
        binding.tvTodoTimeAdd.text=safeArgs.nowTime

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
        binding.tvTodoTimeAdd.setOnClickListener {
            openData()
        }

        return binding.root
    }

    private fun addTodoData() {
        val title = binding.addTodoTitle.text.toString()
        val desc = binding.addTodoDesc.text.toString()
        val status = binding.todoStatus.selectedItemPosition
        val stTime = binding.addTodoStartTime.text.toString()
        val endTime = binding.addTodoEndTime.text.toString()
        val time = binding.tvTodoTimeAdd.text.toString()
        viewModel.addTodo(title, desc, stTime, endTime, status,time)
    }

    private fun spLoad() {
        list = arrayListOf("Easy", "Med...", "Hard")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun openData(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
            Toast.makeText(context, "$dayOfMonth,", Toast.LENGTH_SHORT).show()
            c.set(Calendar.YEAR,year)
            c.set(Calendar.MONTH,month)
            c.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            //val week=getWeek(dayOfMonth-1)

            Log.d(TAG, "openData: ${monthOfYear}")
            val mon=if ("${monthOfYear+1}".length<2) "0${monthOfYear+1}" else "${monthOfYear+1}"
            val day=if ("${dayOfMonth}".length<2) "0${dayOfMonth}" else "${dayOfMonth}"

            val date = LocalDate.parse("$year-$mon-$day")


           binding.tvTodoTimeAdd.text="${date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))}"
        }, year, month, day)

        dpd.show()
    }


}
