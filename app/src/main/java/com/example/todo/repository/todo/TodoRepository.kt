package com.example.todo.repository.todo

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todo.models.TodoModel
import com.example.todo.data.UserDao
import com.example.todo.ui.home.usecase.HomeUseCase

class TodoRepository(private val userDao: UserDao) {

    val takeNowTime: String
        @RequiresApi(Build.VERSION_CODES.O)
        get() = HomeUseCase.takeNowTime()

    suspend fun addTodo(todoModel: TodoModel) {
        userDao.insertTodoData(todoModel)
    }

    suspend fun updateTodo(todoModel: TodoModel) {
        userDao.update(todoModel)
    }

    suspend fun readTodoOfDay(time: String): List<TodoModel> {
        return userDao.readData(time)
    }

    suspend fun readDay(): List<String> {
        return userDao.readDay()
    }

    suspend fun readAllTodo(): List<TodoModel> {
        return userDao.readAllTodo()
    }


}