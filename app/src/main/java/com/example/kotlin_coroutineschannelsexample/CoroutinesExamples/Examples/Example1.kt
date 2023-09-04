package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    // вводим в косоль список пользователей в корутине
    runBlocking {
        launch {
            getUserNames().forEach {
                println(it)
            }
        }
    }
    
}

// создаем метод для создания списка полльзователей
private suspend fun getUserNames(): List<String> {
    val list = mutableListOf<String>()
    list.add(getUser(1))
    list.add(getUser(2))
    list.add(getUser(3))
    list.add(getUser(4))
    return list
}

// Создаем метод для создания строки пользователя с id b и задержкой в 1 секунду
private suspend fun getUser(id: Int): String {
    delay(1000)
    return "User: $id"
}