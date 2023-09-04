package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    println("Main started")
    println("Thread name: " + Thread.currentThread().name + ", Thread id: " + Thread.currentThread().id)
    for (i in 0..1_000_000) {
        async { longRunningWorkSuspend("Coroutine $i", 100*i.toLong()) }
    }
    println("Main ended")
}

private suspend fun longRunningWorkSuspend(coroutineName: String, delay: Long) {
    println("Worker Thread Started")
    println("Thread name: " + Thread.currentThread().name + ", Thread id: ${Thread.currentThread().id} in $coroutineName")
    for (i in 1..9) {
        delay(delay)
        println("Remaining time left for ${Thread.currentThread().name}: " + (10-i) + " in " + coroutineName)
    }
    println("Worker thread Ended")
}