package com.example.listproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listproject.databinding.ActivityMainBinding
import com.example.listproject.mylists.ListCollectionAdapter
import com.example.listproject.mylists.ListDepositoryManager
import com.example.listproject.mylists.data.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.card_layout.view.*

class ListHolder{
    companion object{
        var PickedList:Task? = null
    }
}

class MainActivity : AppCompatActivity() {

    private val TAG:String = "ListProject:MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        signInAnonymously()

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

            if (title.isEmpty())
                Log.e(TAG, "You cannot add a list without a name")
            else
                addList(title)
        }
    }

    private fun onTaskClicked(task:Task):Unit{
        ListHolder.PickedList = task

        val intent = Intent(this,ListDetailsActivity::class.java)

        startActivity(intent)
    }

    private fun addList(title:String){
        val list = Task(title)
        ListDepositoryManager.instance.addList(list)
    }

    private fun signInAnonymously(){

        auth.signInAnonymously().addOnSuccessListener {
            Log.d(TAG,"Login success ${it.user.toString()}")
        }.addOnFailureListener{
            Log.e(TAG,"Login failed ", it)
        }
    }
}