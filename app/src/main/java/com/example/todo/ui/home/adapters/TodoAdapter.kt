package com.example.todo.ui.home.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.models.TodoModel
import com.example.todo.databinding.RcvTodoItemBinding
import com.example.todo.ui.home.HomeFragmentDirections
import com.example.todo.ui.search.adapters.SearchAdapter
import com.example.todo.ui.update.usecase.UpdateUC
import java.util.*
import kotlin.collections.ArrayList

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var list: List<TodoModel> = emptyList()

    private lateinit var binding: RcvTodoItemBinding

    inner class TodoViewHolder(var binding: RcvTodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateModel(todoModel: TodoModel, position: Int) {
            val title=if (todoModel.title.length>20) "${todoModel.title.substring(0,21)}..." else todoModel.title
            binding.tvItemTodoTitle.text=title
            binding.tvItemTodoDesc.text="${todoModel.start}-${todoModel.end}"
            binding.tvStatus.text= Statatus.values()[todoModel.status].name

            binding.statusImg.visibility= if (todoModel.todo) View.VISIBLE else View.INVISIBLE


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = RcvTodoItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.rcv_todo_item, parent, false)
        )

        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        holder.populateModel(list[position],position)

        holder.binding.rowLayout.setOnClickListener {
           val action= HomeFragmentDirections.actionHomeFragmentToUpdateFragment(list[position])
            holder.binding.root.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int = list.size

    fun setData(mList: List<TodoModel>) {
        this.list = mList
        notifyDataSetChanged()
    }


     fun onItemMove(pos1: Int, pos2: Int) {
        if (pos1<pos1){
            for (i in pos1 until pos2){
                Collections.swap(list,i,i+1)
            }
        }else{
            for (i in pos1 downTo pos2+1){
                Collections.swap(list,i,i-1)
            }
        }
        notifyItemMoved(pos1,pos2)
    }

     @RequiresApi(Build.VERSION_CODES.O)
     fun onItemDismiss(position: Int) :Boolean{
         val arrList=list as ArrayList<TodoModel>
         if (UpdateUC.compareTodo(arrList[position].time)) {
             arrList[position] = arrList[position].copy(todo = true)
             list = arrList
             notifyItemChanged(position)
             return true
         }
         notifyItemChanged(position)
         return false
    }

    enum class Statatus{
        Easy,Medi,Hard
    }
}