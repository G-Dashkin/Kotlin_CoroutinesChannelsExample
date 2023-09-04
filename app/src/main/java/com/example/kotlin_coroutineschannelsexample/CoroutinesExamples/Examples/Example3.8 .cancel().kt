package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    // Еще одной полезной возможностью является отмена корутины.

    println("[${Thread.currentThread().name}] Start")
    coroutineScope {
        val job: Job = launch() {
            execSuspend(1000)
            println("[${Thread.currentThread().name}] It will never be printed")
        }
        // Запустим вторую корутину в которой через пол секунды будет отмена первой.
        launch {
            delay(500)
            job.cancel()
        }
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем и как мы видим, первая корутина была запущена, но не была завершена, так как мы не
    // видим строки “It will never be printed” в консоли.

    // Важно!!! отмена suspend-функции может быть произведена только в точке ее приостановке. т.е.
    // нет гарантии, что функция будет отменена сразу при вызове cancel(). При этом внутри отменяемой
    // корутины можно перехватить исключение о том, что она была отменена.
}

private suspend fun execSuspend(ms: Long) {
    println("[${Thread.currentThread().name}] Start do something with delay #$ms ms")
    delay(ms)
    println("[${Thread.currentThread().name}] Finish do something with delay #$ms ms")
}
