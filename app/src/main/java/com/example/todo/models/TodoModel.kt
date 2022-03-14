package com.example.todo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "todo_data")

data class TodoModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String,
    val status: Int,
    val start:String,
    val end:String,
    val time: String,
    val todo:Boolean,
    val daily:Boolean

):Parcelable
