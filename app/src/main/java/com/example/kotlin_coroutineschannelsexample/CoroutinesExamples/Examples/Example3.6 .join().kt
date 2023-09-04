package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    println("Start")
    coroutineScope {
        // Так же с помощью объекта Job можно дождаться выполнения корутины внутри области.
        val job: Job = launch() {
            execSuspend(1000)
        }
        println("Before join()")
        job.join() // Если не вызвать .join(), то программа не будет дожиться окончания работы корутины и сразу выведит в лог Before join() и After join()
        println("After join()")
    }
    println("Finish")
    // Запускаем программу. Сначала в консоль выведем Before join(), затем запустим корутину и
    // выведемм After join(),
}

private suspend fun execSuspend(ms: Long) {
    println("Start do something with delay #$ms ms")
    delay(ms)
    println("Finish do something with delay #$ms ms")
}
