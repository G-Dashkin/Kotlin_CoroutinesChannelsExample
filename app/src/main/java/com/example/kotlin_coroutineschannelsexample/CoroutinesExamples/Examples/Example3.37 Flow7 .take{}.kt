package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

suspend fun main() {
    // Опробуем функцию take().
    val time = measureTimeMillis {
        (1..3).asFlow().take(2).collect {
            println("[${Thread.currentThread().name}] Receive value $it")
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код и в лог выводятся два первых элемента последовательности.

}