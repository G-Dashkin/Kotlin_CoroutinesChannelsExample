package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step10CoroutinesAsyncDeferredSimpleBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CoroutinesActivity3Step10CoroutinesAsyncDeferredSimple : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines3Step10CoroutinesAsyncDeferredSimpleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step10CoroutinesAsyncDeferredSimpleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {

            binding.progress.isVisible = true
            binding.buttonLoad.isEnabled = false

            // 1) Немного упростим прошлый пример, вместо того, чтобы устанавливать значение в TextView
            // в корутинах, мы просто вызовим этим методы и полученное значение будет последним, как нам и нужно
            val deferredCity: Deferred<String> = lifecycleScope.async {
                loadCity()
            }

            // 2) Во второй сделаем тоже самое
            val deferredTemp: Deferred<Int> = lifecycleScope.async {
                loadTemperature()
            }

            lifecycleScope.launch {
                val city = deferredCity.await()
                val temp = deferredTemp.await()

                // 3) И в последней корутине устаноим значение в TextView полученное из объектов Deferred
                binding.tvLocation.text = city
                binding.tvTemperature.text = temp.toString()

                Toast.makeText(
                    this@CoroutinesActivity3Step10CoroutinesAsyncDeferredSimple,
                    "City: $city Temp: $temp",
                    Toast.LENGTH_SHORT)
                    .show()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true

            }
        }

    }

    private suspend fun loadData() {
        Log.d("MyLog", "Load started: $this")
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temp = loadTemperature()
        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
        Log.d("MyLog", "Load finished: $this")
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"
    }

    private suspend fun loadTemperature(): Int {
        delay(5000)
        return 17
    }
}