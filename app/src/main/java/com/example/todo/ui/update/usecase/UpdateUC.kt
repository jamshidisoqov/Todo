package com.example.todo.ui.update.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todo.ui.home.usecase.HomeUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object UpdateUC {


    @RequiresApi(Build.VERSION_CODES.O)
    fun compareTodo(takeTime:String):Boolean{
        val localDate= LocalDate.parse(takeTime, DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))

        return takeTime.equals(HomeUseCase.takeNowTime())||localDate.isAfter(LocalDate.now())
    }
}