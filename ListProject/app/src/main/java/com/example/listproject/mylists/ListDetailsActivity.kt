package com.example.listproject.mylists

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listproject.databinding.ActivityListDetailsBinding

class ListDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailsBinding.inflate(layoutInflater)
    }
}