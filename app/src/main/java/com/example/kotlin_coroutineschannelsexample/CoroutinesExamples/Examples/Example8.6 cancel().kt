package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import java.util.Random
fun main(): Unit = runBlocking {
    // Теперь попробуем отменить корутину после запуска
    // CoroutineStart поменяем на DEFAULT
    val coroutines: List<Deferred<String>> = List(100) {
        async(start = CoroutineStart.DEFAULT) {
            doWork(it.toString())
        }
    }
    // Для отмены корутины нужно вызвать метод
    coroutines.forEach { println(it.cancel()) }
    // Но дополнительно, чтобы отменить выполнение корутины, нужно проверять ее статус is.Active в билдере
}

private suspend fun doWork(name: String): String {
    delay(Random().nextInt(5000).toLong())
    return "Done. $name"
}
