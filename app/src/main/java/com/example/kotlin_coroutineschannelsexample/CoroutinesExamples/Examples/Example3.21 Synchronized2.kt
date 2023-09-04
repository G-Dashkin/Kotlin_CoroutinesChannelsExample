package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

suspend fun main() {

    val c = 100
    val r = 1000
    var counter = 0
    // Проблему состояния гонкия можно исправить ограничив количество потоков в которых выполняется
    // увеличение значния счетчика.
    val singleThreadContext = newSingleThreadContext("Update_context")

    val time = measureTimeMillis {
        withContext(Dispatchers.Default) {

            repeat(c) {
                launch {
                    repeat(r) {
                        withContext(singleThreadContext) {
                            counter++
                        }
                    }
                }
            }

        }
    }
    println("[${Thread.currentThread().name}] Completed ${c * r} actions in $time ms")
    println("[${Thread.currentThread().name}] Completed = $counter")

    // Запускаем код. И это решило проблему, но теперь на переключение контекста уходит очень много
    // времени и из за этого наш код работает а 10 раз медленнее.

}