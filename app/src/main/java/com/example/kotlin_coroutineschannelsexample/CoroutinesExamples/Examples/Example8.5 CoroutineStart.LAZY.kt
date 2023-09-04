package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import java.util.Random
fun main(): Unit = runBlocking {
    // Если мы укажем состояние CoroutineStart.LAZY в корутине, тогда корутины не будет запускаться
    // до вызоваа метода .await().
    // Они все находятся в состоянии Created, но запущены будут только после вызова .await()
    val coroutines: List<Deferred<String>> = List(100) {
        async(start = CoroutineStart.LAZY) {
            doWork(it.toString())
        }
    }
    coroutines.forEach { println(it.await()) }
}

private suspend fun doWork(name: String): String {
    delay(Random().nextInt(5000).toLong())
    return "Done. $name"
}
