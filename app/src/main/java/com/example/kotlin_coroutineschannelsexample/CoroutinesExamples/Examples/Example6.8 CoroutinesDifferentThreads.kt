package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.concurrent.thread

// Сделаем пример с изменением потока запуска корутины
fun main() = runBlocking {
    println("main starts")
    joinAll(
        async{ threadSwitchingCoroutine(1, 500) },
        async{ threadSwitchingCoroutine(3, 300) }
    )
    println("main ends")
    // Запускаем пример смотрим лог, что у корутин сначала один поток, потом другой
}

// Сделаем переключение потока в функции
suspend fun threadSwitchingCoroutine(number: Int, delay: Long){
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay)
    withContext(Dispatchers.Default) {
        println("Coroutine $number has finished on ${Thread.currentThread().name}")
    }
}