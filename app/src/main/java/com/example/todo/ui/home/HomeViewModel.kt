package com.example.todo.ui.home

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.*
import com.example.todo.models.TodoModel
import com.example.todo.data.UserDatabase
import com.example.todo.repository.todo.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(application: Application) : AndroidViewModel(application) {


    var readAllData: LiveData<List<TodoModel>>? = null


    private var repo: TodoRepository? = null

    init {
        val dao = UserDatabase.getDatabase(application).userDao()
        repo = TodoRepository(dao)
        readAllData = repo!!.readAllData
    }

    fun sortingSpinner(item:Int){

        Log.d(TAG, "sortingSpinner: ${readAllData!!.value!!.size}")
        viewModelScope.launch(Dispatchers.IO) {
           //readAllData!!.value  =  repo!!.sortingToDo(item)
        }
    }



}