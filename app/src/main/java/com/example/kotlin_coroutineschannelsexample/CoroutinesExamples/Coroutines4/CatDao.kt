package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// создаем Dao интерфейс - CatDao с методами получения и добавления данных.
@Dao
interface CatDao {
    @Query("SELECT * FROM cat")
    fun getCats(): List<Cat>

    @Insert
    fun insert(cat: Cat)
}