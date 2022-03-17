package com.example.todo.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.todo.databinding.SearchFragmentBinding
import com.example.todo.ui.search.adapters.SearchAdapter

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: SearchFragmentBinding
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragmentBinding.inflate(layoutInflater)


        adapter= SearchAdapter()

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)


        viewModel.readAllData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        binding.rcvSearch.adapter=adapter

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.homeSearchIc.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.adapterChange(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.adapterChange(newText!!)
                return true
            }

        })
        binding.homeSearchIc.setOnCloseListener(object :androidx.appcompat.widget.SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                viewModel.readAllTodo()
                return true
            }

        })

        return binding.root
    }

    override fun onResume() {
        viewModel.readAllTodo()
        super.onResume()
    }


}