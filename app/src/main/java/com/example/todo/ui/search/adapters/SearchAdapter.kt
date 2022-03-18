package com.example.todo.ui.search.adapters

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
import com.example.todo.ui.search.SearchFragmentDirections
import com.example.todo.ui.update.usecase.UpdateUC

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.TodoViewHolder>() {
    private var list: List<TodoModel> = emptyList()

    private lateinit var binding: RcvTodoItemBinding

    inner class TodoViewHolder(var binding: RcvTodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun populateModel(todoModel: TodoModel) {
            val title = if (todoModel.title.length > 20) "${
                todoModel.title.substring(
                    0,
                    21
                )
            }..." else todoModel.title
            binding.tvItemTodoTitle.text = title
            binding.tvItemTodoDesc.text = "${todoModel.start}-${todoModel.end}"
            binding.tvStatus.text = Statatus.values()[todoModel.status].name
            binding.statusImg.visibility = if (todoModel.todo) View.VISIBLE else View.INVISIBLE
            binding.statusImgNot.visibility=
                if (UpdateUC.compareTodo(todoModel.time)) View.INVISIBLE else if (todoModel.todo)
                    View.INVISIBLE else View.VISIBLE



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        binding = RcvTodoItemBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.rcv_todo_item, parent, false)
        )

        return TodoViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        holder.populateModel(list[position])

        holder.binding.rowLayout.setOnClickListener {
            val action =
                SearchFragmentDirections.actionSearchFragmentToUpdateFragment(list[position])
            holder.binding.root.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int = list.size

    fun setData(mList: List<TodoModel>) {
        this.list = mList
        notifyDataSetChanged()
    }

    enum class Statatus {
        Easy, Medi, Hard
    }

}