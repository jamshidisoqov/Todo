package com.example.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.databinding.RcvDayItemBinding
import com.example.todo.models.MyCalendar

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private var list: List<MyCalendar> = emptyList()
    private lateinit var binding: RcvDayItemBinding

    class CalendarViewHolder(binding: RcvDayItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateModel() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        binding = RcvDayItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.rcv_day_item, parent, false)
        )

        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 20

    fun setData(mList: List<MyCalendar>) {
        this.list = mList
        notifyDataSetChanged()
    }
}