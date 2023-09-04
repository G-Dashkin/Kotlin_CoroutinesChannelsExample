package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {
    val time = measureTimeMillis {

        // Теперь попробуем запустить эту функцию в корутиние, а параллельно ей запустим еще одну,
        // но обе эти корутины будут работать в однопоточном контексте.
        withContext(newSingleThreadContext("Single_thread_context")){
            launch {
                repeat(3) {
                    delay(100)
                    println("[${Thread.currentThread().name}] I'm working!")
                }
            }
            launch {
                getList().forEach { i ->
                    println("[${Thread.currentThread().name}] Receive value $i")
                }
            }
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускакем код и как мы видим, эти корутины работают последовательно, а не параллельно.
    // Сначала выполнилась вторая корутина, а затем первая.

}

private suspend fun getList() = sequence {
    for (i in 1..3) {
        Thread.sleep(100)
        println("[${Thread.currentThread().name}] Generate value $i")
        yield(i)
    }
}