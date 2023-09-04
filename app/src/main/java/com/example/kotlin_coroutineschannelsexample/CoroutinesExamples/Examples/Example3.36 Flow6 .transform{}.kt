package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

suspend fun main() {

    val time = measureTimeMillis {
        // В асинхронных потоках есть метод transform() который позволят реализовать более сложный
        // вариант преобразования данных. Например, мы можем преобразовать один входящий элемент в
        // несколько исходящих
        (1..3).asFlow().transform {
            emit(it * 2)
            emit(it * 3)
        }.collect{
            println("[${Thread.currentThread().name}] Receive value $it")
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код, все работает

}