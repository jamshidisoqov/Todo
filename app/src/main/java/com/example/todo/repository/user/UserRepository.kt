package com.example.todo.repository.user

import androidx.lifecycle.LiveData
import com.example.todo.models.User
import com.example.todo.data.UserDao

class UserRepository (private val userDao: UserDao){

    val readAllData:LiveData<List<User>>  =  userDao.readAllData()

    fun addUser(user: User){
        userDao.insertData(user)
    }

}