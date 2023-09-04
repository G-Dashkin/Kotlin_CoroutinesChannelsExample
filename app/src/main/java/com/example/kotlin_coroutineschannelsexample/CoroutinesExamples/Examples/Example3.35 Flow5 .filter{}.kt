package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

suspend fun main() {

    val time = measureTimeMillis {
        // Реализуем фильтрацию элементов. Добавляем фильтр.
        (1..3).asFlow().filter {
            it % 2 == 0
        }.collect{
            println("[${Thread.currentThread().name}] Receive value $it")
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код и в лог выводятся только отфильтрованные элементы.

}