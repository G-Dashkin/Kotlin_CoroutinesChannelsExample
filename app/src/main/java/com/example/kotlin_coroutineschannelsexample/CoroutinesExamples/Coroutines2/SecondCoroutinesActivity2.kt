package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlin_coroutineschannelsexample.databinding.ActivitySecondCoroutines2Binding

class SecondCoroutinesActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySecondCoroutines2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondCoroutines2Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}