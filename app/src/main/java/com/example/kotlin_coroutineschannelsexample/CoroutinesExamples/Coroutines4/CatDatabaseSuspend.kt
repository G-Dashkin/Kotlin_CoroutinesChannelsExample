package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Coroutines4

import androidx.room.Database
import androidx.room.RoomDatabase

// Создаем базу данных
@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class CatDatabaseSuspend: RoomDatabase() {
    abstract fun catDaoSuspend(): CatDaoSuspend
}