package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {

    // Для решения пролемы переполнения памяти в блокирующем коде, мы могли бы использовать
    // последовательности (Sequence) или блокирующую очередь (BlockingQueue).

    // Аналогом блокирующей очереди в асинхронном мире является – Канал (Channel)
    // Каналы позволяют передавать последовательность элементов из одной корутины в другую.
    // Интерфейс Channel реализуют два основных метода,
    // send() – для отправки элементов в канал
    // receive() – для получение элементов из каналов

    // Перепишем наш код с использованием канала.

    val time = measureTimeMillis {
        coroutineScope {
            // Создаем канал
            val channel = Channel<Int>()

            // Первая корутина отправляет элементы в канал
            launch{
                getList(channel)
            }

            // Вторая корутина получает элементы из канала
            launch {
                repeat(3) {
                    println("[${Thread.currentThread().name}] Receive value ${channel.receive()}")
                }
            }
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код и здесь мы сначала создаем канал, а затем запускаем две корутины. Первая корутина
    // отправляет элементы в канал, а вторая получает элементы из канала. Обратите внимание, что парамтер
    // channel в функции getList() объявлен как SendChannel – это означает, что внутри этой функции мы
    // собираемся только  отправлять элементы в канал, но не получать их.

}

// Передаем канал в качестве объекта и заполняем его
private suspend fun getList(channel: SendChannel<Int>) {
    for (i in 1..3) {
        delay(100)
        channel.send(i * i)
    }
}