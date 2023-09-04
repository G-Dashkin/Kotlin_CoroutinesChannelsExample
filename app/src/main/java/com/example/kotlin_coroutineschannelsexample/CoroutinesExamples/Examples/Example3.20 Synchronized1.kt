package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

suspend fun main() {
    // Теперь рассмотрим способы синхронизации доступов к памяти при использовании корутин
    // Есть 3 способа:
    // Однопоточные контексты
    // Atomic - потокобезопасные типы данных
    // Mutex - Критические секции

    // Мы не можем гарантировать, что наша suspend-функция всегда будет вызываться с однопоточным
    // диспетчером, поэтому нужно иметь представление, как происходити синхронизация доступа к
    // разделяемым ресурсам в многопоточных приолжениях.

    //Рассмотрим проблему синхронизации при помощи следующего кода.

    val c = 100
    val r = 1000
    // Добавим корутин билдер, счетчик и выведим в лог.
    var counter = 0

    val time = measureTimeMillis {
        withContext(Dispatchers.Default) {
            // Здесь мы запускаем 100 корутин, каждая из которых выполняет 1000 инктиментаций счетчика counter
            repeat(c) {
                launch {
                    repeat(r) {
                        counter++
                    }
                }
            }

        }
    }
    println("[${Thread.currentThread().name}] Completed ${c * r} actions in $time ms")
    println("[${Thread.currentThread().name}] Completed = $counter")

    // Запускаем код,
    // В большинстве случаев этот код вернет значение счетвика менее 100 000

    // Это происходит из за того, что корутины запущенная в несколько потоков пытаются одновременно
    // получать и устанавливать значения четчика, что приводит к состоянию гонки.

}