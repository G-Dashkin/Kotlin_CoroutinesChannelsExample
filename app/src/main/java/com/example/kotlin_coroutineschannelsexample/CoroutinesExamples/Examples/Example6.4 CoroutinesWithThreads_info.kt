package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

// Теперь посмотрим названия потоков запускаемых в корутинах
fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { coroutineWithThreadInfo(1, 500) },
        async { coroutineWithThreadInfo(2, 300) }
    )
    println("main ends")
    // Запускаем приложение и видим зазвания потоков в корутинах (в данном случае это главный поток)
} 

// Снова создаем suspend-функцию с выводом в лог названия потока
suspend fun coroutineWithThreadInfo(number: Int, delay: Long){
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay) // Возвращаем метод delay() для приостановке
    println("Coroutine $number has finished on ${Thread.currentThread().name}")
}