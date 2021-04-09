package com.example.listproject.mylists

import com.example.listproject.mylists.data.ListDetails

class ItemDepositoryManager {
    private lateinit var itemCollection:MutableList<ListDetails>

    var onItem:((List<ListDetails>) -> Unit)? = null

    fun loadItems(){
        itemCollection = mutableListOf(
            ListDetails("Eggs"),
            ListDetails("Bananas"),
            ListDetails("Bread")
        )

        onItem?.invoke(itemCollection)
    }

    fun addItem(item:ListDetails){
        itemCollection.add(item)
        onItem?.invoke(itemCollection)
    }

    companion object{
        val instance = ItemDepositoryManager()
    }
}