package com.example.todo.ui.add

import android.app.Application
import android.text.TextUtils
import android.text.format.DateFormat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.models.TodoModel
import com.example.todo.data.UserDatabase
import com.example.todo.models.TodoTime
import com.example.todo.repository.todo.TodoRepository
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTodoViewModel(application: Application) : AndroidViewModel(application) {
    private var repo: TodoRepository? = null

    private var todoTime: MutableLiveData<TodoTime> =
        MutableLiveData<TodoTime>(TodoTime("12:00", "12:00"))
    val _todoTime: LiveData<TodoTime> get() = todoTime

    init {
        val dao = UserDatabase.getDatabase(getApplication()).userDao()
        repo = TodoRepository(dao)
    }

    fun addTodo(
        title: String,
        desc: String,
        stTime: String,
        endTime: String,
        status: Int,
        nowTime: String
    ): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkTodo(title, desc, stTime, endTime)) {
                val todoModel =
                    TodoModel(0, title, desc, status, stTime, endTime, nowTime, false, false)
                repo!!.addTodo(todoModel)
            }
        }
        return false
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

    fun checkTodo(title: String, desc: String, stTime: String, endTime: String): Boolean {
        return !TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(stTime) && !TextUtils.isEmpty(
            endTime
        )
    }


    fun setStartTime(manager: FragmentManager, p0: Int) {
        openTimerPicker(manager, p0)
    }

    fun setEndTime(manager: FragmentManager, p0: Int) {
        openTimerPicker(manager, p0)
    }

}