package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.random.Random

fun main() {
    // Также можно выделить пулл потоков для запуска корутин при помощи newFixedThreadPoolContext()
    println("[${Thread.currentThread().name}] Start")
    runBlocking {
        newFixedThreadPoolContext(5, "Five-thread-context").use { context ->
            withContext(context) {
                repeat(5) { i ->
                    launch {
                        println("[${Thread.currentThread().name}] Start coroutine #$i")
                        delay(Random.nextLong(1000L))
                        println("[${Thread.currentThread().name}] Finish coroutine #$i")
                    }
                }
            }
        }
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем и из полученного результата видно, что корутина возобоновляет свое выполнение в
    // любом свободном потоке из пула, но не обязательно в том, в котором она начала свое выполнение.

}