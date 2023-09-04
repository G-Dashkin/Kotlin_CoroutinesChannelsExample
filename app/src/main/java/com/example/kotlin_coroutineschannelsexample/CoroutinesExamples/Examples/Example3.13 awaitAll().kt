package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    // Мы можем сформировать коллекцию deferred объектов, чтобы в дальнейшем вызвать на ней метод
    // awaitAll() и дождаться получения результатов от всех экземпляров.
    println("[${Thread.currentThread().name}] Start")
    coroutineScope {
        val deferredList = (1..3).map { i ->
            async {
                execSuspend(i * 1000L)
            }
        }
        val doubleList = deferredList.awaitAll()
        println("[${Thread.currentThread().name}] Overall delay time: ${doubleList.sum()} sec.")
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем код и получаем общий результат выполнения.
    // У нас выводится в консоль сумма всех результатов
}

private suspend fun execSuspend(ms: Long): Double {
    println("[${Thread.currentThread().name}] Start do something with delay #$ms ms")
    delay(ms)
    println("[${Thread.currentThread().name}] Finish do something with delay #$ms ms")
    return  ms.toDouble() / 1000
}
