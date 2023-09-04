package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.room.Entity
import androidx.room.PrimaryKey

// Создадим класс - Cat для формирования полей в таблице базы данных.
@Entity
data class Cat (
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val name: String,
    val breed: String?,
    val age: Int,
)