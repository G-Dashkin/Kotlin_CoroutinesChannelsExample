package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines5ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines5Binding

class CoroutinesActivity5 : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines5Binding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.doSomeApiCalls()


    }
}