package com.example.todo.ui.home

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.todo.models.TodoModel
import com.example.todo.data.UserDatabase
import com.example.todo.repository.todo.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var repo: TodoRepository? = null

    private var _todOfDay:MutableLiveData<List<TodoModel>> = MutableLiveData<List<TodoModel>>()
    val todoOfDay:LiveData<List<TodoModel>> get() = _todOfDay

    private var _nowTime:MutableLiveData<String> = MutableLiveData<String>()
    val nowTime:LiveData<String> get() = _nowTime

    private var _days:MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    val days:LiveData<List<String>> get() = _days

    init {
        val dao = UserDatabase.getDatabase(application).userDao()
        repo = TodoRepository(dao)
        readDay()
        takeNowTime(HomeUseCase.takeNowTime())
    }

    fun readTodoOfDay(time:String){
        viewModelScope.launch{
         _todOfDay.value = repo!!.readTodoOfDay(time)
        }
    }

    fun takeNowTime(time: String){
        _nowTime.value=time
        readTodoOfDay(time)
    }

    fun readDay(){
        viewModelScope.launch{
            _days.value=repo!!.readDay()
        }
    }

    fun updateTodoStatus(todoModel: TodoModel)
    {
        viewModelScope.launch(Dispatchers.IO){
            repo!!.updateTodo(todoModel)
        }

    }

}