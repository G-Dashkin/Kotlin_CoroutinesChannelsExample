package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlin.system.measureTimeMillis

fun main() {

    // По умолчанию у нас создаются небуфферизированне рандеув каналы и отправека сообщения в такие
    // каналы возможна только в том случае, если слушатель готов принимать сообщения.

    // Но что если обработка сообщения занимает достаточно много времени и выполняется медленнее, чем
    // отправка сообщения в канал?

    // Выражение select позволяет реализоать алгоритм при котором сообщение пришедшее пока основной
    // канал занят, будет отправляться в дополнительный канал.

    //    Напишем пример такого алгоритма,
    val time = measureTimeMillis {
        runBlocking {
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