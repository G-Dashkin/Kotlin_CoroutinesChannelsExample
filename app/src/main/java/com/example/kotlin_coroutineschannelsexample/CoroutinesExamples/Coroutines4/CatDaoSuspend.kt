package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CatDaoSuspend {
    // чтобы было все правильно, добавим suspend к функциям, которые осуществляют работу с базой данных.
    @Query("SELECT * FROM cat")
    suspend fun getCats(): List<Cat>

    @Insert
    suspend fun insert(cat: Cat)
}