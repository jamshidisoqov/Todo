package com.example.todo.ui.add

import android.app.Application
import android.os.Build
import android.text.TextUtils
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
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
import com.example.todo.ui.update.usecase.UpdateUC
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun addTodo(
        title: String,
        desc: String,
        stTime: String,
        endTime: String,
        status: Int,
        nowTime: String
    ): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkTodo(title, desc, stTime, endTime,nowTime)) {
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
                var hour1=if ("${picker.hour}".length<2) "0${picker.hour}" else "${picker.hour}"
                var minute1=if ("${picker.minute}".length<2) "0${picker.minute}" else "${picker.minute}"
                todoTime.value =
                    TodoTime("$hour1:$minute1", todoTime.value!!.endTime)
            } else {
                var hour1=if ("${picker.hour}".length<2) "0${picker.hour}" else "${picker.hour}"
                var minute1=if ("${picker.minute}".length<2) "0${picker.minute}" else "${picker.minute}"
                todoTime.value =
                    TodoTime(todoTime.value!!.startTime, "$hour1:$minute1")
            }
        }

        return "12:00"

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkTodo(title: String, desc: String, stTime: String, endTime: String, time: String): Boolean {
        return UpdateUC.compareTodo(time)&&!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(stTime) && !TextUtils.isEmpty(
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