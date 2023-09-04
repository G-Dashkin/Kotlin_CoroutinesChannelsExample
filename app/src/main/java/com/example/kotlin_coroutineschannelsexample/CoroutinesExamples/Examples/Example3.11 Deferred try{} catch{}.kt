package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    // Так как Deferred это наследник Job, то на его экземплярах мы можем вызывать те же методы,
    // что и на объектах Job. Например, мы можем отложить выполнение корутины при помощи параметра
    // CoroutineStart.LAZY или отменить ее выполнеие перехватив исключение.
    println("[${Thread.currentThread().name}] Start")
    coroutineScope {
        val deferred: Deferred<Double> = async(start = CoroutineStart.LAZY) {
            // Добавим try catch и метод .also {}
            try {
                execSuspend(1000).also {
                    println("[${Thread.currentThread().name}] It will never be printed")
                }
            } catch (e: CancellationException) {
                println("[${Thread.currentThread().name}] My Deferred was cancelled because of ${e.message}")
                0.0
            }
        }
        println("[${Thread.currentThread().name}] Before start")
        deferred.start()
        launch {
            delay(500)
            deferred.cancel("I don't want to wait - [Отмена]")
        }
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем код. И так мы сделали отложенный запуск объекта deferred и отменили его выполнение.
}

private suspend fun execSuspend(ms: Long): Double {
    println("[${Thread.currentThread().name}] Start do something with delay #$ms ms - [Deferred]")
    delay(ms)
    println("[${Thread.currentThread().name}] Finish do something with delay #$ms ms - [Deferred]")
    return  ms.toDouble() / 1000
}
