package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.concurrent.thread

// Так как корутины являются легковесными потоками можно запускать сразу мииллионы корутин за раз
// в отличвае от потоков. Ниже пример на корутинах. Выведем 1 000 000 в лог
fun main() = runBlocking {
    repeat(1_000_000) {
        launch {
            delay(5000)
            print(".")
        }
    }
    // Запускаем приложение и в лог выводится 1 000 000 точек через 5 секунд
} 
