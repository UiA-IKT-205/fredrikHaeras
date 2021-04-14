package com.example.listproject.mylists

import com.example.listproject.mylists.data.Task

class ListDepositoryManager {
    lateinit var listCollection:MutableList<Task>

    var onList:((List<Task>) -> Unit)? = null

    fun loadList(){
        listCollection = mutableListOf(

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