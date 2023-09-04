package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines4Step4Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CoroutinesActivity4Step4 : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutines4Step4Binding
    val catDatabaseSuspend by lazy {
        Room.databaseBuilder(
            applicationContext,
            CatDatabaseSuspend::class.java,
            "database_suspend_cat"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines4Step4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        addNewCat()
        checkCatCount()
        // Запускаем приложение и теперь он запускается в другом потоке. Так как мы указали
        // необходимый диспатчер.
    }

    private fun addNewCat() {
        val newCat = Cat(name = "Vasya", breed = "Siberian", age = 3)
        // Сейчас определение потока для работы с базой происзодит по уполчанию
        // Укажем явно деспатчер, в каком потоке будет происходить загрузка - Dispatchers.IO
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d("MyLog","Thread: ${Thread.currentThread().name}")
            catDatabaseSuspend.catDaoSuspend().insert(newCat)
        }
    }

    private fun checkCatCount() {
        val result = lifecycleScope.async {
            val cats = catDatabaseSuspend.catDaoSuspend().getCats()
            launch(Dispatchers.Main) {
                binding.catCountTextView.text = "Cat count is ${cats.size}"
            }
        }
    }
}