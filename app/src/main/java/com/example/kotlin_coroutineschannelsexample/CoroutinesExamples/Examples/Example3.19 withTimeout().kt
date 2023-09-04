package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    // Есть более удобный способ запуска коуртин с таймаутами. Для этого в корутинах есть специальная
    // функции – withTimeout() и withTimeoutOrNull()
    println("[${Thread.currentThread().name}] Start")
    runBlocking {
        withTimeout(1000) {}
        withTimeoutOrNull(1000) {}
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем приложение
    // withTimeout() – выбросит ислючение в случае достижения таймаута
    // withTimeoutOrNull() – вернет либо значение лямбды либо Null
}