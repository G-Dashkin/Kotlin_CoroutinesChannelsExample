package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.selects.select
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {

    // Так же выражение select можно использовать, в функции onAwait {}, onJoin {} и onTimeOut {}
    // Рассмотрим onAwait {}
    val time = measureTimeMillis {
        runBlocking {
            // С помощью функции async мы запустили 5 корутин, каждая из которых возвращает результат,
            // через случайный промежуток времени
            val deferredList = (1..5).map { i ->
                async {
                    val time = Random.nextLong(1000)
                    println("[${Thread.currentThread().name}] $i: will delay for $time ms")
                    delay(time)
                    i
                }
            }
            // Выражение select позволяет получить результат из той корутины, которая завершится первой.
            select {
                deferredList.forEach {
                    it.onAwait { i ->
                        println("[${Thread.currentThread().name}] Selected result from #$i deferred")
                    }
                }
            }
    val mainChannel = Channel<Int>()
    val sideChannel = Channel<Int>()
            launch {
                mainChannel.consumeEach {
                    println("[${Thread.currentThread().name}] Consume in main channel #$it")
                    delay(250)
                }
            }
            launch {
                sideChannel.consumeEach {
                    println("[${Thread.currentThread().name}] Consume in side channel #$it")
                }
            }

            repeat(10) {
                delay(100)
                select {
                    mainChannel.onSend(it) {}
                    sideChannel.onSend(it) {}
                }
            }
            coroutineContext.cancelChildren()
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код и в консоле мы видим, что все сообщения, которые не обработались основным каналом
    // (mainChannel), отправились в дополнительный.

}