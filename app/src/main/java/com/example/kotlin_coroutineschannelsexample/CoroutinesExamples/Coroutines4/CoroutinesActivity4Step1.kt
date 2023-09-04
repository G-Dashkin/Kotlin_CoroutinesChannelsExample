package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.kotlin_coroutineschannelsexample.databinding.ActivityCoroutines4Step1Binding

class CoroutinesActivity4Step1 : AppCompatActivity() {
    private lateinit var binding: ActivityCoroutines4Step1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutines4Step1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Если мы просто так запутим приложение, то оно упадет. Так как нельзя получить доступ к
        // базе данных на главном потоке, поскольку это потенциально может заблокирвоать UI на
        // длительный период
        addNewCat()
    }

    // в MainActivity создадим метод для добавления котов в базу
    private fun addNewCat() {
        val catDatabase = Room.databaseBuilder(
            applicationContext,
            CatDatabase::class.java,
            "database_cat"
        ).build()

        // Таким образом при каждом запуске приложения у нас создается новый объект т.е. id у объекта
        // будет новый, а имя и парода одни и те же. Но такое нельзя делать на главном потоке.
        // Для этого мы будем использовать корутины
        val newCat = Cat(name = "Vasya", breed = "Siberian", age = 3)
        catDatabase.catDao().insert(newCat)
    }
}