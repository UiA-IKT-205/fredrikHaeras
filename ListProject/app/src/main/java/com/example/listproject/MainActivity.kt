package com.example.listproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listproject.databinding.ActivityMainBinding
import com.example.listproject.mylists.ListCollectionAdapter
import com.example.listproject.mylists.ListDepositoryManager
import com.example.listproject.mylists.data.Task
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toDoList.layoutManager = LinearLayoutManager(this)
        binding.toDoList.adapter = ListCollectionAdapter(emptyList<Task>(), this::onTaskClicked)

        val lists = ListDepositoryManager.instance
        lists.onList = {
            (binding.toDoList.adapter as ListCollectionAdapter).updateCollection(it)
        }

        ListDepositoryManager.instance.loadList()

        binding.addBtn.setOnClickListener{
            val title = binding.newList.text.toString()

            binding.newList.setText("")

            addList(title)
        }
    }

    private fun onTaskClicked(task:Task):Unit{

    }

    private fun addList(title:String){
        val list = Task(title)
        ListDepositoryManager.instance.addList(list)
    }
}