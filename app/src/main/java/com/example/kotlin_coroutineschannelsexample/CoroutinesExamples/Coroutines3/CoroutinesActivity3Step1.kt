package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.kotlin_coroutineschannelsexample.R
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines3Step1Binding

class CoroutinesActivity3Step1 : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines3Step1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines3Step1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoad.setOnClickListener {
            loadData()
        }
    }

    // Сейчас приложение блокируется при нажатии на кнопку, так как запускается функция усыпления основного потока
    // это имитация выполнения какой то функции которая блокирует выполнение основного потока.

    // Данный код является "Синхронным" т.к. он выполняется "последоватлельно"
    private fun loadData() {
        binding.progress.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temp = loadTemperature(city)
        binding.tvTemperature.text = temp.toString()
        binding.progress.isVisible = false
        binding.buttonLoad.isEnabled = true
    }

    private fun loadCity(): String {
        Thread.sleep(5000)
        return "Moscow"
    }

    private fun loadTemperature(city: String): Int {
        Toast.makeText(this, getString(R.string.loading_temperature_toast, city), Toast.LENGTH_SHORT).show()
        Thread.sleep(5000)
        return 17
    }

}