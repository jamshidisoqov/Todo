package com.example.todo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.todo.MainActivity
import com.example.todo.R
import com.example.todo.adapters.CalendarAdapter
import com.example.todo.adapters.SpinnerAdapter
import com.example.todo.adapters.TodoAdapter

import com.example.todo.databinding.HomeFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {


    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var adapter: TodoAdapter
    private lateinit var cAdapter: CalendarAdapter
    private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var list: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomeFragmentBinding.inflate(layoutInflater)
        //List load
        spLoad()

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        //generate adapters
        adapter = TodoAdapter(activity as MainActivity)
        cAdapter = CalendarAdapter()
        spinnerAdapter = SpinnerAdapter(requireContext(), list)

        //set adapters
        binding.todoWorkRcv.adapter = adapter
        binding.calendarRcv.adapter = cAdapter
        binding.todoStatusHome.adapter = spinnerAdapter

        //viewmodelgenerate

        //adapter setdata
        viewModel.readAllData!!.observe(viewLifecycleOwner) {

                adapter.setData(it)
        }

        //add todo
        binding.addFab.setOnClickListener {
            (activity as MainActivity).onNewFragment()

        }
        //settings
        binding.homeImgProf.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        binding.todoStatusHome.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.sortingSpinner(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        return binding.root
    }

    private fun spLoad() {
        list = arrayListOf("All", "Easy", "Med...", "Hard")
    }



}