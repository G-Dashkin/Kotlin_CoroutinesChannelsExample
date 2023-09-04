package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

// Рассмотрим пример получения данныз из корутин через .async{} и await()
fun main() = runBlocking {

    // 2) Вместо launch{} вызываем билдер async {} для получения результата выполнения suspend-функции
    val dataUser = async { getUserFromNetwork() }
    val localUser = async { getUserFromDatabase() }

    // 3) Выведем в лог результат проверки содержимосго корутин
    println("dataUser: ${dataUser.await()}")
    println("localUser: ${localUser.await()}")
    if (dataUser.await() == localUser.await()) {
        println("Equals")
    } else {
        println("not Equals")
    }
}

// 1) Оставляем 2 наши suspend-функции имитирующие полученние данных из сети и из базы данных
private suspend fun getUserFromNetwork(): String {
    delay(1000)
    return "Tom"
}

private suspend fun getUserFromDatabase(): String {
    delay(2000)
    return "Mike"
}

