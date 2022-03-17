package com.example.todo.ui.search

import android.app.Application
import androidx.lifecycle.*
import com.example.todo.data.UserDatabase
import com.example.todo.models.TodoModel
import com.example.todo.repository.todo.TodoRepository
import com.example.todo.ui.search.usecase.SearchUC
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private var _readAllDate:MutableLiveData<List<TodoModel>> = MutableLiveData<List<TodoModel>>()
    val readAllData:LiveData<List<TodoModel>> get() = _readAllDate
    private var repo: TodoRepository? = null
    init {
        val dao = UserDatabase.getDatabase(application).userDao()
        repo = TodoRepository(dao)
        readAllTodo()
    }


    fun readAllTodo(){
        viewModelScope.launch {
            _readAllDate.value = repo!!.readAllTodo()
        }
    }

    fun adapterChange(newString: String){
        _readAllDate.value=SearchUC.changeNewList(newString,_readAllDate.value!!)
    }
}