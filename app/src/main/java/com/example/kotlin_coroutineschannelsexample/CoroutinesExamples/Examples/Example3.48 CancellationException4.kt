package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {
    // Отмена корутин очень тесно связана с исключениями, так как внутри корутина использует исключение
    // CancellationException для отмены и этот тип исключения игнорируется хэндлерами, поэтому его
    // можно использовать только для получения отладочной информации.

    // При исползовании билдера launch {} перехватить исключение CancellationException можно внутри корутины.

    println("[${Thread.currentThread().name}] Start execution")
    val job = launch {
        try {
            delay(Long.MAX_VALUE)
        } catch (e: CancellationException) {
            println("[${Thread.currentThread().name}] Coroutine was cancelled ($e)")
        }
    }
    yield()
    job.cancelAndJoin()
    yield()
    println("[${Thread.currentThread().name}] End execution")

    // Запускаем код и здесь при вызове функции .cancelAndJoin() исколючение было перехвачено внутри корутины

}