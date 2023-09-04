package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {

    val c = 100
    val r = 1000
    var counter = 0
    // Третий способ решения проблемы парралелизма, это взаимное исключение - Mutex подразумещие
    // использование критических секций кода, которые гарантировано не будут выполняться одновременно.
    val mutex = Mutex()

    val time = measureTimeMillis {
        withContext(Dispatchers.Default) {
            repeat(c) {
                launch {
                    repeat(r) {
                        mutex.lock()
                        try {
                            counter++
                        } finally {
                            mutex.unlock()
                        }
                    }
                }
            }

        }
    }
    println("[${Thread.currentThread().name}] Completed ${c * r} actions in $time ms")
    println("[${Thread.currentThread().name}] Completed = $counter")

    // Запускаем код и все так же работает, но время выполнения немного увичилось.

}