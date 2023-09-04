package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    // Отмена suspend-функции может быть произведена только в точке ее приостановке. т.е.
    // нет гарантии, что функция будет отменена сразу при вызове cancel(). При этом внутри отменяемой
    // корутины можно перехватить исключение о том, что она была отменена.

    println("[${Thread.currentThread().name}] Start")
    coroutineScope {
        // Обернем работу корутину в блок try{} catch{}
        // Внутри отменяемой корутины будет перехвачено исключение о том, что она была отменена
        val job: Job = launch() {
            try {
                execSuspend(1000)
                println("[${Thread.currentThread().name}] It will never be printed")
            } catch (e: CancellationException) {
                println("[${Thread.currentThread().name}] My job was cancelled because of ${e.message}")
            }

        }

        launch {
            delay(500)
            job.cancel("I don't want to wait") // добавим сообщение в метод .cancel()
            // т.е. два сообщения сцепились в одно
        }
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запустим программу. И мы можем перехватить сообщение о том, почему была отменена корутина.

}

private suspend fun execSuspend(ms: Long) {
    println("[${Thread.currentThread().name}] Start do something with delay #$ms ms")
    delay(ms)
    println("[${Thread.currentThread().name}] Finish do something with delay #$ms ms")
}
