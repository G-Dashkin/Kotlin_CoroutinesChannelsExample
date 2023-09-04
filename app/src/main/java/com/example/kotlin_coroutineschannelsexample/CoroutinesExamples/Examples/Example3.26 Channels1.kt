package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {
    // Теперь рассмотрим каналы в корутинах.
    // Ранее мы исопльзовали тип Deferred для получения единственного значения из корутины. Функция
    // async и .await() очень удобны, когда нам нужно получиь единственное значение.
    // Но что если нам нужо вернуть из корутины коллекцию элементов?

    // Например, есть какая то suspend-функция, которая собирает список из нескольких чисел и при
    // этом получение каждого из них занимает определенное время.

    val time = measureTimeMillis {
        coroutineScope {
            // Мы можем как и раньше запустить корутину с помощью acync{}
            val deferred = async {
                getList()
            }
            //  и дождаться получения результата через .await()
            val list = deferred.await()
            for (i in list) {
                println("[${Thread.currentThread().name}] Receive value $i")
            }
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")

    // Запускаем код и получаем вывод в лог. Этот подход хорошо работает для небольших коллекций, но
    // если в коллекуии сотни тысыч или миллионы элементов, то мы очень быстро столкнемся с переполнением
    // памяти или недопустимо большим временем получения ответа.
}

private suspend fun getList(): List<Int> = buildList {
    // Сделаем добавление элементов в функцие
    for (i in 1..3) {
        delay(100)
        add(i * i)
    }
}