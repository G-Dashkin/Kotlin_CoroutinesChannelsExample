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
            // 2) Создадим два канала один для помещения значений в канала. Второй для получения
            val channel1 = getList()
            val channel2 = processList(channel1)
            channel2.consumeEach {
                println("[${Thread.currentThread().name}] Receive value $it")
            }
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код, все работает.
}

private suspend fun CoroutineScope.getList(): ReceiveChannel<Int> = produce {
    for (i in 1..3) {
        delay(100)
        send(i * i)
    }
    close()
}

// 1) Так же каналы можно объединять в пайплайны – это такой паттерн, при котором элементы полученные
// из одного канала подаются на вход другого. Таким образом каналы объединяются в цеполчку.
// Типы каналов различаются. Первый канал типа Int, второй канал типа String
private suspend fun CoroutineScope.processList(channel: ReceiveChannel<Int>): ReceiveChannel<String> = produce {
    channel.consumeEach {
        // Здесь мы применили фильтрацию и дополнительную обработку каждого элемента
        if (it < 10) {
            send("square is $it")
        }
    }
}