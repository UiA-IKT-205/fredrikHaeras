package com.example.listproject.mylists

import android.net.Uri
import com.example.listproject.mylists.data.Task

class ListDepositoryManager {
    private lateinit var listCollection:MutableList<Task>

    var onList:((List<Task>) -> Unit)? = null
    var onSave:((List:Uri) -> Unit)? = null

    fun loadList(){
        listCollection = mutableListOf(
            Task("Shopping list"),
            Task("House Cleaning"),
            Task("Work")
        )

        onList?.invoke(listCollection)
    }

    fun addList(list:Task){
        listCollection.add(list)
        onList?.invoke(listCollection)
    }

    companion object{
        val instance = ListDepositoryManager()
    }
}