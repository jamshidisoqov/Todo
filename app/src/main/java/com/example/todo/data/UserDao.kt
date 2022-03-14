package com.example.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.models.TodoModel
import com.example.todo.models.User
import org.jetbrains.annotations.NotNull

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertData(user: User)

    @Query("SELECT*FROM user_data ORDER BY id ASC")
    fun readAllData():LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodoData(todo: TodoModel)

    @NotNull
    @Query("SELECT*FROM todo_data")
    fun readTodo():LiveData<List<TodoModel>>

    @Update(entity = TodoModel::class)
    fun update(todo: TodoModel)
}