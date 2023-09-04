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
            // 3) Здесь меняем
            val channel = getList()
            channel.consumeEach {
                println("[${Thread.currentThread().name}] Receive value $it")
            }
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код, все также работает и теперь нам не надо явно запускать корутины, кроме того мы
    // избавились от необходимости передавать канал как параметр функции getList(). Теперь мы получаем
    // канал, как результат выполнения этой функции.

}

// 2) Теперь принимающая сторона не знает заранее сколько элементов будет в канале и обробатывает их до
// тех под, пока канал не будет закрыт.

// Премер кода, который мы пишем, реализует паттерн – “Производитель-Получатель”. Для него в библиотеке
// Channels есть специальный билдер каналов - produce() и функция расширения consumeEach() для обработки элементов.
private suspend fun CoroutineScope.getList(): ReceiveChannel<Int> = produce {
    for (i in 1..3) {
        delay(100)
        send(i * i)
    }
    // 1) В отличае от очередей, каналы можно закрывать и для принимающей стороны это будет означать,
    // что в канале больше не появится эленметов.
    //    channel.close()
    close()
}