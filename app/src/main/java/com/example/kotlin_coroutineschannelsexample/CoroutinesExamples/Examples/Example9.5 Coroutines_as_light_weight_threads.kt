package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking{
    println("Main started")
    println("Thread name: " + Thread.currentThread().name + ", Thread id: " + Thread.currentThread().id)
    async {
        val coroutineName = "Sample Coroutine"
        println("$coroutineName started")
        for (i in 0..9) {
            delay(1000)
            println("Remaining time left for $coroutineName: " + (10-i))
        }
        println("$coroutineName ended")
    }
    println("Main ended")
}