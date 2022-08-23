package com.sarang.todoappkotlin.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sarang.todoappkotlin.R
import com.sarang.todoappkotlin.data.models.Priority
import com.sarang.todoappkotlin.data.models.ToDoData
import com.sarang.todoappkotlin.data.viewModel.SharedViewModel
import com.sarang.todoappkotlin.data.viewModel.ToDoViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel : SharedViewModel by viewModels()
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)

        view.title_et.setText(args.currentItem.title)
        view.description_et.setText(args.currentItem.description)
        view.priorities_spinner.setSelection(parsePriority(args.currentItem.priority))
        view.priorities_spinner.onItemSelectedListener =  sharedViewModel.listener
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> removalOfItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun removalOfItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _ , _ ->
            toDoViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(), "Successfully removed ${args.currentItem.title}?", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){ _ , _ -> }
        builder.setTitle("Delete ${args.currentItem.title}?")
        builder.setMessage("Are you sure you want to remove ${args.currentItem.title}?")
        builder.show()
    }


    private fun parsePriority(priority: Priority) : Int{
        return when(priority){
            Priority.HIGH ->  0
            Priority.MEDIUM ->  1
            Priority.LOW ->  2
        }
    }

    private fun updateItem(){
        val title = title_et.text.toString()
        val description = description_et.text.toString()
        val priority = priorities_spinner.selectedItem.toString()
        val validation = sharedViewModel.verifyDataFromUser(title, description)

        if (validation){
            val updatedItem = ToDoData(args.currentItem.id, title, sharedViewModel.parsePriority(priority), description)
            toDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully Updated!!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all the fields..", Toast.LENGTH_SHORT).show()
        }
    }

}