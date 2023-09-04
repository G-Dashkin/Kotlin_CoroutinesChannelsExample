package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    println("[${Thread.currentThread().name}] Start")
    coroutineScope {
        // Также мы можем получить результат от нескольких корутин запущенных одновременно.
        val deferred1: Deferred<Double> = async {
            execSuspend(1000)
        }
        val deferred2: Deferred<Double> = async {
            execSuspend(2000)
        }

        // Получаем результат выполнения работы корутин
        val result1 = deferred1.await()
        val result2 = deferred2.await()

//        И выведем результаты в лог.
        println("[${Thread.currentThread().name}] Overall delay time: ${result1 + result2} sec.")

    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем код и получаем общий результат выполнения.
}

private suspend fun execSuspend(ms: Long): Double {
    println("[${Thread.currentThread().name}] Start do something with delay #$ms ms")
    delay(ms)
    println("[${Thread.currentThread().name}] Finish do something with delay #$ms ms")
    return  ms.toDouble() / 1000
}
