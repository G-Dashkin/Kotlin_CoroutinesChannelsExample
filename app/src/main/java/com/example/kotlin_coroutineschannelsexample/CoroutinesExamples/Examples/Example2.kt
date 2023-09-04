package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {

    launch {
        val time = measureTimeMillis {
            val answer1 = async { networkCall1() }
            val answer2 = async { networkCall2() }
            println("Answer1 is ${answer1.await()}")
            println("Answer2 is ${answer2.await()}")
        }
        println("Requests took $time ms.")

    }

}

suspend fun networkCall1(): String {
    delay(3000L)
    return "Answer 1"
}

suspend fun networkCall2(): String {
    delay(3000L)
    return "Answer 2"
}