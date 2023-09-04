package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.concurrent.thread

// Запустим такой же пример с потоками.
fun main() {
    repeat(1_000_000) {
        thread {
           Thread.sleep(5000)
           print(".")
        }
    }
    // Если мы запустим такое приложение, то оно упадет, если на устаройстве будет недостаточно памяти
} 
