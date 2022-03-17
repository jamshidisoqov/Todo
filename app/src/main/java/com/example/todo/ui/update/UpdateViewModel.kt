package com.example.todo.ui.update

import android.app.Application
import android.text.format.DateFormat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.UserDatabase
import com.example.todo.models.TodoModel
import com.example.todo.models.TodoTime
import com.example.todo.repository.todo.TodoRepository
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel(application: Application) : AndroidViewModel(application) {

    var todoModel: MutableLiveData<TodoModel>? = null

    private var todoTime: MutableLiveData<TodoTime> =
        MutableLiveData<TodoTime>()
    val _todoTime: LiveData<TodoTime> get() = todoTime


    fun updateUser(todoModel: TodoModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val dao = UserDatabase.getDatabase(getApplication()).userDao()
            val repository = TodoRepository(dao)
            repository.updateTodo(todoModel)
        }
    }

    fun openTimerPicker(manager: FragmentManager, pos: Int): String {
        val timerPICKER = DateFormat.is24HourFormat(getApplication())
        val clockFormat = if (timerPICKER) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Set Todo Time")
            .build()
        picker.show(manager, "Tag")

        picker.addOnPositiveButtonClickListener {
            if (pos == 0) {
                todoTime.value =
                    TodoTime("${picker.hour}:${picker.minute}", todoTime.value!!.endTime)
            } else {
                todoTime.value =
                    TodoTime(todoTime.value!!.startTime, "${picker.hour}:${picker.minute}")
            }
        }

        return "12:00"

    }

    fun setStartTime(manager: FragmentManager, p0: Int) {
        openTimerPicker(manager, p0)
    }

    fun setEndTime(manager: FragmentManager, p0: Int) {
        openTimerPicker(manager, p0)
    }


}