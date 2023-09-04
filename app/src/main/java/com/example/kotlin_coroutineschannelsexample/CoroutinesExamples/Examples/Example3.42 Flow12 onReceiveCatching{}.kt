package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlin.system.measureTimeMillis

fun main() {

    val time = measureTimeMillis {
        runBlocking {
            val fizz = produce {

                while (true) {
                    delay(90)
                    send("Fizz")
                }
            }
            val buzz = produce {
                // 2) Если закрыть один их этих каналов раньше, то мы получим исключение.
//                while (true) {
                    delay(200)
                    send("Buzz")
//                }
            }

            repeat(7) { i ->
                // 1) select ожидает, что все каналы с которыми он работает, будут открыты при каждом его вызове.
                select {
                    // 3) Чтобы обработать эту ошибку, заменем onReceive{} на .onReceiveCatching{}
                    // И получим результат. Сделаем проверку на null и вывод в лог.
                    fizz.onReceiveCatching { result ->
                        val value = result.getOrNull()
                        if (value != null) {
                            println("[${Thread.currentThread().name}] #$i, $value selected")
                        } else {
                            println("[${Thread.currentThread().name}] #$i, channel is closed")
                        }
                    }
                    buzz.onReceiveCatching { result ->
                        val value = result.getOrNull()
                        if (value != null) {
                            println("[${Thread.currentThread().name}] #$i, $value selected")
                        } else {
                            println("[${Thread.currentThread().name}] #$i, channel is closed")
                        }
                    }
                }
            }
            coroutineContext.cancelChildren()

        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код. И теперь мы будем корректно обрабатывать ситуацию, когда канал закрыт.
}