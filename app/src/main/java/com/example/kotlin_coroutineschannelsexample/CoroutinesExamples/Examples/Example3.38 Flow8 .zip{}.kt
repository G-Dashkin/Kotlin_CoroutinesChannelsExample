package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

suspend fun main() {
    // функцию zip() которая позволяет объеденить две последовательности
    val time = measureTimeMillis {
        (1..3).asFlow().zip((4..6).asFlow()) { f1, f2 ->
            f1 + f2
        }.collect {
            println("[${Thread.currentThread().name}] Receive value $it")
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")
    // Запустим код. Все работает.
}