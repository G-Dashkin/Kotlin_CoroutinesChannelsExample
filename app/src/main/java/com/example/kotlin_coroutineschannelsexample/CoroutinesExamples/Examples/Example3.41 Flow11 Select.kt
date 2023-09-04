package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlin.system.measureTimeMillis

fun main() {

    // Рассмотри выражение Select – оно позволяет ожидать завершения нескольких suspend-функций и
    // обрабатывает результат первой завершившийся из них. Данный API является эксперементальным и в
    // последствии может быть изменено.

    // Select обрабатывает первую завершившуюся suspend-функцию
    // Основные методы:
    // onReceive - обработка первого результата
    // onSend - отправка в дополнительный канал
    // onAwait - обработка первого результата из списка

    // Рассмотрим на примере,
    // Создадим два канала, каждый из которых отправляет сообщения, через заданные промежутки времени.
    val time = measureTimeMillis {
        runBlocking {
            val fizz = produce {
                while (true) {
                    delay(90)
                    send("Fizz")
                }
            }
            val buzz = produce {
                while (true) {
                    delay(200)
                    send("Buzz")
                }
            }
            // И 7 раз подряд вызываем выражение select{} при каждом вызове оно ожидает первое
            // сообщение из обоих каналов и выводит в консоль первое полученное сообщение.
            repeat(7) { i ->
                // select ожидает, что все каналы с которыми он работает, будут открыты при каждом его вызове.
                select {
                    fizz.onReceive { value ->
                        println("[${Thread.currentThread().name}] #$i, $value selected")
                    }
                    buzz.onReceive { value ->
                        println("[${Thread.currentThread().name}] #$i, $value selected")
                    }
                }
            }
            coroutineContext.cancelChildren()

        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

//    Запускаем код. Выражение select было вызвано 7 раз. 5 раз из канала Fizz и 2 из канала Buzz.

}