package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {

    println("Main " + Thread.currentThread().name)
    // Укажем отдельный поток в диспатчере и выведем в лог название потока.
    val job = launch(Dispatchers.IO) {
        println("Coroutine 2 " + Thread.currentThread().name)
        repeat(1000) {
            println("$it \uD83d\uDc7b")
            delay(300)
        }
    }
    delay(3000)
    job.cancel()
    println("End")

    // Запускаем программу
    // И в логе у нас выводится главная корутина, которая запускается в главном потоке, далее
    // запускается вторая корутина, в отдельном потоке, который называется DefaultDispatcher-worker-1
    // т.е. это какой то другой поток как раз предназначенный для длительных операций ввода-вывода.

}



