package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {
    // Теперь рассмотрим отмену дочерних корутин. Если отменить дочернюю корутину, то родительская
    // все равно продолжит работать.
    // Изменем код. Добавим два лаунча.
    println("[${Thread.currentThread().name}] Start execution")
    val parentJob = launch {
        val childJob = launch {
            try {
                delay(Long.MAX_VALUE)
            } catch (e: CancellationException) {
                println("[${Thread.currentThread().name}] Coroutine was cancelled ($e)")
            }
        }
        yield()
        childJob.cancelAndJoin()
        yield()
        println("[${Thread.currentThread().name}] Parent job is working")
    }
    parentJob.join()
    println("[${Thread.currentThread().name}] End execution")

    // Запускаем код. Дочерняя корутина была отменена, а родительская продолжила свою работу.
}