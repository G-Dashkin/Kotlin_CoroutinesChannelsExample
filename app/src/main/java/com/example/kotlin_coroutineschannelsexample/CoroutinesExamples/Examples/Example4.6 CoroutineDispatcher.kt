package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(array: Array<String>) = runBlocking {

    // Рассмотрим диспатчер – этот параметр определяет в каком потоке корутина будет работать.

    println(coroutineContext[Job])
    val job = launch(Dispatchers.IO) {
        // Здесь может выполняться какая то загрузка чего либо
    }

    // Есть следующие виды диспатчеров:
    // Default - диспатчер по умолчанию (launch, async). Используеся общий пул потоков

    // IO - для долгих операций ввода-выода. Используется общий пул потоков JVM (может совпадать с Default)

    // Main - диспетчер для запуска на главном потоке (UI). Прямо говорит, что корутину нужно
    // запустить в главном потоке. Это нужно, когда мы получаем данные от сервера и нам нужно показать
    // их на экране, а к UI нельзя обращаться из бэкГраунда.

    // Unconfined - конкретный поток не указан. Корутина будет запущена на том же потоке, где только,
    // что происходило выполнение.

}



