package com.example.todo.repository.todo

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.todo.models.TodoModel
import com.example.todo.data.UserDao

class TodoRepository(private val userDao: UserDao) {

    val readAllData:LiveData<List<TodoModel>> = userDao.readTodo()


    suspend fun addTodo(todoModel: TodoModel){
        userDao.insertTodoData(todoModel)
    }

    suspend fun updateTodo(todoModel: TodoModel){
        userDao.update(todoModel)
    }

    suspend fun sortingToDo(item:Int):List<TodoModel> {

        if (item == 0) return readAllData!!.value!!
        var list:ArrayList<TodoModel>?=null
        for (i in readAllData.value!!){
            Log.d(TAG, "sortingToDoData: ${i}")
            if (i.status==item){
                list!!.add(i)
            }
        }

        val list1: List<ArrayList<TodoModel>?> = listOf(list)



        return list1 as List<TodoModel>
    }



}