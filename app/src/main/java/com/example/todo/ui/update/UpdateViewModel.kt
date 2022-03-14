package com.example.todo.ui.update

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.CurrentTodo
import com.example.todo.data.UserDatabase
import com.example.todo.models.TodoModel
import com.example.todo.repository.todo.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel(application: Application) : AndroidViewModel(application) {

    var todoModel: MutableLiveData<TodoModel>?=null


    fun updateUser(todoModel: TodoModel){
        viewModelScope.launch(Dispatchers.IO){
            val dao=UserDatabase.getDatabase(getApplication()).userDao()
            val repository=TodoRepository(dao)
            repository.updateTodo(todoModel)
        }
    }


}