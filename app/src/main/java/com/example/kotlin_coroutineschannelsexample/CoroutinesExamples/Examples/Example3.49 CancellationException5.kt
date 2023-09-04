package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {
    // Поеняем .launch{ } на .async{} изменем название переменной
    println("[${Thread.currentThread().name}] Start execution")
    val deferred = async {
        try {
            delay(Long.MAX_VALUE)
        } catch (e: CancellationException) {
            println("[${Thread.currentThread().name}] Coroutine was cancelled ($e)")
        }
    }
    yield()
    // и у нее вызовем функцию .cancel()
    deferred.cancel()

    // добаим блок try catch
    try {
        deferred.await()
    } catch (e: CancellationException) {
        println("[${Thread.currentThread().name}] Await exception ($e)")
    }
    yield()
    println("[${Thread.currentThread().name}] End execution")

    // Запускаем код,
    // Первый раз исключение было перехвачено внутри корутины и второй раз снаружи

    // В данном примере мы впервые использовали функцию yield() – она создает точку разрыва в корутине,
    // для того, чтобы позволить выполниться другим корутинам или обработать событие отмены.


}