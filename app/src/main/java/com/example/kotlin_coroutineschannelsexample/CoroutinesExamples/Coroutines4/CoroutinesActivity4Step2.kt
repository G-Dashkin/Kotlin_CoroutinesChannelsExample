package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines4Step1Binding
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines4Step2Binding
import kotlinx.coroutines.launch

class CoroutinesActivity4Step2 : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines4Step2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines4Step2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Запускаем приложение. Оно теперь не падает и в базу должен быть добавлен новый объект.
        addNewCat()
    }

    private fun addNewCat() {
        val catDatabaseSuspend = Room.databaseBuilder(
            applicationContext,
            CatDatabaseSuspend::class.java,
            "database_suspend_cat"
        ).build()
        val newCat = Cat(name = "Vasya", breed = "Siberian", age = 3)
        // Создадим корутину, которая будет жить только в рамках нашей активити. т.е. мы хотим
        // привязаться к скоупу нашей активити. Сначала добавим зависимость для lifecycle
        // Сейчас запустим обычный лонч и в нем будем добавлять объекты в базу.
        lifecycleScope.launch {
            // Так как в корутине нужно запускать suspend-функции для работы с базой данных
            // был создан отдельный интерфейс с suspend-функциями и база данных этого интрфейса
            // Прошлый вариант закоментим
            // catDatabase.catDao().insert(newCat)
             catDatabaseSuspend.catDaoSuspend().insert(newCat)
        }
    }
}