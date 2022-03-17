package com.example.todo.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object HomeUseCase {

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseToLocalDate(time:String):LocalDate{
        val localDate= LocalDate.parse(time, DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
        return localDate
    }


    @RequiresApi(Build.VERSION_CODES.O)
     fun takeNowTime():String{
        val localDate=LocalDateTime.now()
        val format = localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
        return format
     }

    @RequiresApi(Build.VERSION_CODES.O)
    fun LocalDateToParseTime(time: String):String{
        val format= HomeUseCase.parseToLocalDate(time)
        return "${format.dayOfWeek.name.substring(0,3)},${format.dayOfMonth} ${format.month.name}"
    }
}