package com.mason.test

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mason.test.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main2)
        setContentView(binding.root)
    }

    fun guess(view: View) {

        val num = binding.number.text.toString()

        Log.d("MainActivity", "guess ${num}" )
    }
}