package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {

    val c = 100
    val r = 1000
    var counter = 0
    val mutex = Mutex()

    val time = measureTimeMillis {
        withContext(Dispatchers.Default) {
            repeat(c) {
                launch {
                    repeat(r) {
                        // Есть иной вариант исопльзоваться Мьютекса. Все работает так же.
                        mutex.withLock {
                            counter++
                        }
                    }
                }
            }

        }
    }
    println("[${Thread.currentThread().name}] Completed ${c * r} actions in $time ms")
    println("[${Thread.currentThread().name}] Completed = $counter")

    // Запускаем код и все так же работает
}