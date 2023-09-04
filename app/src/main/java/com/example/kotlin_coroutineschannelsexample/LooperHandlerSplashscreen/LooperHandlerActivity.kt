package com.example.kotlin_coroutineschannelsexample.LooperHandlerSplashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityLooperHandlerBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LooperHandlerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLooperHandlerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLooperHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartCoroutine.setOnClickListener {
            Log.d("MyLog", "Main Activity thread started")
            CoroutineScope(Dispatchers.IO).launch {
                longRunningWorkSuspend("SampleCoroutine", 1000)
            }
            Log.d("MyLog", "Main Activity thread ended")
        }

        binding.btnStarFor.setOnClickListener {
            Log.d("MyLog", "Main Activity thread started")
                longRunningWork("SampleCoroutine", 1000)
            Log.d("MyLog", "Main Activity thread ended")
        }

        binding.btnStartHandler.setOnClickListener {
            Log.d("MyLog", "Main Activity thread started")
            longRunningWorkHandler("SampleCoroutine", 1000)
            Log.d("MyLog", "Main Activity thread ended")
        }
    }

    private suspend fun longRunningWorkSuspend(coroutineName: String, delay: Long) {
        Log.d("MyLog", "$coroutineName thread started")
        for (i in 1..9) {
            delay(delay)
            Log.d("MyLog", "$coroutineName is progress, remaining time is ${10-i}, ${Thread.currentThread().name}")
        }
        Log.d("MyLog", "$coroutineName thread ended")
    }

    private fun longRunningWork(coroutineName: String, delay: Long) {
        Log.d("MyLog", "$coroutineName thread started")
        for (i in 1..9) {
            Log.d("MyLog", "$coroutineName is progress, remaining time is ${10-i}, ${Thread.currentThread().name}")
        }
        Log.d("MyLog", "$coroutineName thread ended")
    }

    // Данная функция делаем тоже самое, что и корутина, но через Хэндлер
    private fun longRunningWorkHandler(coroutineName: String, delay: Long) {
        Log.d("MyLog", "$coroutineName thread started")
        for (i in 1..9) {
            Handler(Looper.getMainLooper()).postDelayed(kotlinx.coroutines.Runnable {
                try {
                    Thread.sleep(delay)
                    Log.d("MyLog", "$coroutineName is progress, remaining time is ${10-i}, ${Thread.currentThread().name}")
                } catch (ex: Exception) {
                    Log.d("MyLog", "${ex.message}")
                }
            }, 0)
        }
        Log.d("MyLog", "$coroutineName thread ended")
    }
}

