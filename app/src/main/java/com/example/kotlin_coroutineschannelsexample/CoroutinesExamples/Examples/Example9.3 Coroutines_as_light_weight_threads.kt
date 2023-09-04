package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    println("Main started")
    println("Thread name: " + Thread.currentThread().name + ", Thread id: " + Thread.currentThread().id)
    async { longRunningWorkSuspend("Sample Coroutine", 1000) }
    println("Main ended")
}

private suspend fun longRunningWorkSuspend(coroutineName: String, delay: Long) {
    println("$coroutineName started")
    println("Thread name: " + Thread.currentThread().name + ", Thread id: ${Thread.currentThread().id} in $coroutineName")
    for (i in 1..9) {
        delay(delay)
        println("Remaining time left for $coroutineName: " + (10-i))
    }
    println("$coroutineName ended")
}