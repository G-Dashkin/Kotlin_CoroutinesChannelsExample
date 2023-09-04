package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {

    // По умолчанию конструктор каналов создает небуферизированный канал, (он так же известен под
    // названием Рандеву Канал). И он также передает эелментны, только в том случае, если отправитель
    // и получатель встречаются. т.е. первый готов отправлять, а второй готов получать элементы.

    // Чтобы создать буферизированный канал необходимо в конструктор или билдер produce{} добавить
    // парметр capacity – это позволит каналу накапливать указанное количество элементов.

    // Сделаем имитацию быстрой генерации элементов

    val time = measureTimeMillis {
        coroutineScope {
            val channel = getList()
            channel.consumeEach {
                // Добавим их медленную обработку и буффер нашему каналу.
                delay(100)
                println("[${Thread.currentThread().name}] $it")
            }
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код и мы видим, что все элементы сначала были отправлены в канал, а затем обработаны.

}

private suspend fun CoroutineScope.getList(): ReceiveChannel<Int> = produce(capacity = 5) {
    for (i in 1..5) {
        send(i * i)
    }
    println("[${Thread.currentThread().name}] All elements are sent!")
}