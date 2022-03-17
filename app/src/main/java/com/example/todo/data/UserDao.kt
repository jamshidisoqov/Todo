package com.example.todo.data

import androidx.room.*
import com.example.todo.models.TodoModel

@Dao
interface UserDao {

    @Query("SELECT*FROM todo_data WHERE time=:time")
    suspend fun readData(time:String):List<TodoModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodoData(todo: TodoModel)

    @Update(entity = TodoModel::class)
    suspend fun update(todo: TodoModel)

    @Query("SELECT DISTINCT time FROM todo_data")
    suspend fun readDay():List<String>

}