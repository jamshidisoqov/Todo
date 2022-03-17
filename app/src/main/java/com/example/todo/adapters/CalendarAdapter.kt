package com.example.todo.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.RcvDayItemBinding
import com.example.todo.ui.home.HomeFragment
import com.example.todo.ui.home.HomeUseCase

class CalendarAdapter(var fragment: HomeFragment) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private var list: List<String> = emptyList()
    private lateinit var binding: RcvDayItemBinding

    class CalendarViewHolder(var binding: RcvDayItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun populateModel(s: String) {
            val local=HomeUseCase.parseToLocalDate(s)
            binding.tvDay.text=local.dayOfMonth.toString()
            binding.tvWeekday.text=local.dayOfWeek.name.substring(0,3)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        binding = RcvDayItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.rcv_day_item, parent, false)
        )

        return CalendarViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.populateModel(list[position])
        holder.binding.root.setOnClickListener {
            fragment.notifyTodoAdapter(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    fun setData(mList: List<String>) {
        this.list = mList
        notifyDataSetChanged()
    }

}