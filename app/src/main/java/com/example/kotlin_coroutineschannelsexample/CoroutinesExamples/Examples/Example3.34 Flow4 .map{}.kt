package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

suspend fun main() {
    // Асинхронные потоки поддерживают фунции для трансформации наборов данных, внутри которых можно
    // вызвать suspend-функции:
    // .map{} - мапинг
    // .filter{} - фильтр
    // .transform{} - преобразование
    // .take{} - ограниечние количества
    // .zip{} - объудинение
    // .combine{} - комбинация

    // Рассмотрим приемеры использования таких функций.
    val time = measureTimeMillis {
        // Реализуем классический маппинг с использованием функции delay()
        (1..3).asFlow().map {
            delay(100)
            it * 2
        }.collect{
            println("[${Thread.currentThread().name}] Receive value $it")
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код. Все работает верно.

}