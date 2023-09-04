package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {
    // Такие построители как launch{} и actor{} обращаются с возникшим в них исключениями как с
    // необробатываемымми.
    // Изменем код,

    val job = GlobalScope.launch {
        println("[${Thread.currentThread().name}] Throwing exception from async")
        throw ArithmeticException()
    }
    try {
        job.join()
        println("[${Thread.currentThread().name}] joined failed job")
    } catch (e: ArithmeticException) {
        println("[${Thread.currentThread().name}] Caught ArithmeticException")
    }

    // Запускаем приложение, в этом примре блок catch не будет будет выполнен
}