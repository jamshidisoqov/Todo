package com.example.todo.ui.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.ui.home.adapters.CalendarAdapter
import com.example.todo.ui.home.adapters.SpinnerAdapter
import com.example.todo.ui.home.adapters.TodoAdapter

import com.example.todo.databinding.HomeFragmentBinding
import com.example.todo.ui.home.usecase.HomeUseCase

class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var cAdapter: CalendarAdapter
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: List<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomeFragmentBinding.inflate(layoutInflater)
        //List load
        spinnerLoad()

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        //generate adapters
        adapter = TodoAdapter()
        cAdapter = CalendarAdapter(this)
        spinnerAdapter = SpinnerAdapter(requireContext(), list)

        //set adapters
        binding.todoWorkRcv.adapter = adapter
        binding.calendarRcv.adapter = cAdapter

        viewModel.todoOfDay.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        viewModel.days.observe(viewLifecycleOwner) {
            cAdapter.setData(it)
        }

        notifyTodoAdapter(viewModel.nowTime.value!!)


        //add todo
        binding.addFab.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToAddTodoFragment(viewModel.nowTime.value!!)
            findNavController().navigate(action)
        }
        //settings
        binding.homeImgProf.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }


        val itemTouchHelper = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {

                val dragFlag =
                    ((ItemTouchHelper.DOWN or ItemTouchHelper.LEFT) or (ItemTouchHelper.UP or ItemTouchHelper.END))
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlag, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val todoModel =
                    viewModel.todoOfDay.value!![viewHolder.adapterPosition].copy(todo = true)
                viewModel.updateTodoStatus(todoModel)
                if(adapter.onItemDismiss(viewHolder.adapterPosition))
                    Toast.makeText(context, "Succes todo working", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "Can't working of todo", Toast.LENGTH_SHORT).show()
            }

        }

        binding.homeSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        val itemTouch = ItemTouchHelper(itemTouchHelper)
        itemTouch.attachToRecyclerView(binding.todoWorkRcv)


        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun notifyTodoAdapter(s: String) {
        viewModel.takeNowTime(s)
    }

    fun spinnerLoad() {
        list = listOf("All", "Easy", "Medi", "Hard")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        viewModel.readDay()
        viewModel.takeNowTime(HomeUseCase.takeNowTime())

        super.onResume()
    }


}