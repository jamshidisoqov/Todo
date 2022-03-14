package com.example.todo.ui.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todo.models.TodoModel
import com.example.todo.data.UserDatabase
import com.example.todo.repository.todo.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTodoViewModel (application: Application): AndroidViewModel(application) {
    private var repo:TodoRepository?=null

    suspend fun addTodo(todoModel: TodoModel){
        viewModelScope.launch(Dispatchers.IO) {
            val dao = UserDatabase.getDatabase(getApplication()).userDao()
            repo = TodoRepository(dao)
            repo!!.addTodo(todoModel)
        }
    }




}