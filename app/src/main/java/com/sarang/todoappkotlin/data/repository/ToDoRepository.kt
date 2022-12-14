package com.sarang.todoappkotlin.data.repository

import androidx.lifecycle.LiveData
import com.sarang.todoappkotlin.data.ToDoDao
import com.sarang.todoappkotlin.data.models.ToDoData

class ToDoRepository(private val toDoDao : ToDoDao) {
    val getAllData : LiveData<List<ToDoData>> = toDoDao.getAllDara()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData){
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }
}