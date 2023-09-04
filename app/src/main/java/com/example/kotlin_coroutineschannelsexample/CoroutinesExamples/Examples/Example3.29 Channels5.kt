package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {

    val time = measureTimeMillis {
        coroutineScope {
            // 2) И добавим каналы для запуска.
            val channel = Channel<Int>()
            repeat(3) {
                launch {
                    channel.sendElements()
                }
            }
            repeat(3) {
                launch {
                    repeat(3) {
                        println("[${Thread.currentThread().name}] Receive value ${channel.receive()}")
                    }
                }
            }
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код, у нас было сгенерировано 3 набора элементов. И эти наборы обрабатывались разными корутинами.


}

// 1) Писать и читать в канал могут одновременно несоколько корутин. Таким образом можно распараллелить
// добавление элемнтов или их обработку.
private suspend fun SendChannel<Int>.sendElements() {
    for (i in 1..3) {
        delay(100)
        send(i * i)
    }
}