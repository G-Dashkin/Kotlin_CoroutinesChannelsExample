package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun main() {

    val c = 100
    val r = 1000
    var counter = 0
    val singleThreadContext = newSingleThreadContext("Update_context")

    val time = measureTimeMillis {
        // Чтобы избежать постоянного переключения контекста, запустим все наши корутины в скоупе с
        // однопоточным контекстом
        withContext(singleThreadContext) {
            repeat(c) {
                launch {
                    repeat(r) {
//                        withContext(singleThreadContext) {
                            counter++
//                        }
                    }
                }
            }

        }
    }
    println("[${Thread.currentThread().name}] Completed ${c * r} actions in $time ms")
    println("[${Thread.currentThread().name}] Completed = $counter")

    // Запускаем код и теперь он работает быстрее и состояния гонки нет.

}