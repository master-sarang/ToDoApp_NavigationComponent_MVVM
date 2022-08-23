package com.sarang.todoappkotlin.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarang.todoappkotlin.R
import com.sarang.todoappkotlin.data.viewModel.SharedViewModel
import com.sarang.todoappkotlin.data.viewModel.ToDoViewModel
import com.sarang.todoappkotlin.databinding.FragmentListBinding
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val adapter : ListAdapter by lazy { ListAdapter() }
    private val mToDoViewModel : ToDoViewModel by viewModels()
    private val mSharedViewModel : SharedViewModel by viewModels()
    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListBinding.inflate(inflater, container, false)

        setupRecyclerView()

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabseIsEmpty(data)
            adapter.setData(data)
        })

        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer{
            showEmptyDatabaseView(it);
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setupRecyclerView(){
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun showEmptyDatabaseView(emptyDatabse : Boolean) {
       if (emptyDatabse) {
            view?.no_data_textView?.visibility  = View.VISIBLE
       }else {
           view?.no_data_textView?.visibility  = View.INVISIBLE
       }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            removalOfAllItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removalOfAllItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _ , _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully deleted all data", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){ _ , _ -> }
        builder.setTitle("Delete?")
        builder.setMessage("Are you sure you want to remove all the data?")
        builder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}