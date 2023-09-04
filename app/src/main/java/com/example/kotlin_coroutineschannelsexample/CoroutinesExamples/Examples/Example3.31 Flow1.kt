package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() {

    // Рассмотрим Асинхронные потоки.

    // Ранее мы реализовывали передачу последовательности элеменов между корутинами с помощью каналов.

    // Каналы удобный способ передчи коллекции, но у них есть минус, они являются горячими т.е. мы не
    // можем просто сбросить ссылку на receiveChannel потому что отправитель наполняющий канал данными
    // будет бесконечно ожидат получателя, занимая память, оставляя незакрытими сетевые подключения и
    // другие ресурсы.

    // Если нам нужен простой, неблокирующий поток данных с ленивой генерацией элементов. А примитивы
    // синхронизации, генерация обработки элеметов в несколько потоков не нужно.

    // Для таких задач в корутинах есть тип – Flow это аналог последовательности из синхронного мира.
    // Flow реализет холодные асинхронные потоки.

    // Рассмотрим работу асинхронных потоков на примере. Сначала напишем обычную синхронную функцию
    // которая возвращает последовательность генерирующую элементы каждые 100 миллесекунд.

    val time = measureTimeMillis {
        getList().forEach { i ->
            println("[${Thread.currentThread().name}] Generate value $i")
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код и элемены генерируются каждые 100 миллесекунд.

}

private fun getList() = sequence {
    for (i in 1..3) {
        Thread.sleep(100)
        println("[${Thread.currentThread().name}] Generate value $i")
        yield(i)
    }
}