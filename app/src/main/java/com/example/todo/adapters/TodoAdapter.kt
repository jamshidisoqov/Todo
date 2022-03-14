package com.example.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.data.CurrentTodo
import com.example.todo.models.TodoModel
import com.example.todo.databinding.RcvTodoItemBinding
import com.example.todo.ui.home.HomeFragment
import com.example.todo.ui.home.HomeFragmentDirections

class TodoAdapter(var activity: MainActivity) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    private var list: List<TodoModel> = emptyList()

    private lateinit var binding: RcvTodoItemBinding

    inner class TodoViewHolder(var binding: RcvTodoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateModel(todoModel: TodoModel, position: Int) {

            binding.tvItemTodoTitle.text=todoModel.title
            binding.tvItemTodoDesc.text="${todoModel.start}-${todoModel.end}"


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
            HomeFragmentDirections.actionHomeFragmentToUpdateFragment(list[position])
            holder.binding.root.findNavController().navigate(R.id.action_homeFragment_to_updateFragment)

        }

    }

    override fun getItemCount(): Int = list.size

    fun setData(mList: List<TodoModel>) {
        this.list = mList
        notifyDataSetChanged()
    }
}