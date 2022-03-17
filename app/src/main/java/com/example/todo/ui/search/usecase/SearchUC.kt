package com.example.todo.ui.search.usecase

import com.example.todo.models.TodoModel

object SearchUC {

    fun changeNewList(newText:String,list: List<TodoModel>):List<TodoModel>{
        val newList=ArrayList<TodoModel>()


        list.forEach {
            if (it.title.contains(newText)||it.desc.contains(newText)){
                newList.add(it)
            }
        }

     return newList
    }
}