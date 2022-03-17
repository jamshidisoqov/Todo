package com.example.todo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.todo.R
import com.example.todo.databinding.SpinnerItemBinding
import com.example.todo.ui.add.AddTodoFragment

class SpinnerAdapter(ctx:Context, var list: List<String>) : BaseAdapter() {
    private lateinit var binding: SpinnerItemBinding

    override fun getCount(): Int = list.size

    override fun getItem(p0: Int): String=list[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var itemView=if (p1==null){
            LayoutInflater.from(p2!!.context).inflate(R.layout.spinner_item,p2,false)
        }else{
            p1
        }

        binding= SpinnerItemBinding.bind(itemView)

        binding.spStatus.text=list[p0]

        return binding.root

    }

}