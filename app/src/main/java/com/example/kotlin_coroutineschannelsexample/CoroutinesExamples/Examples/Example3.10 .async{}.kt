package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    // Теперь рассмотрим корутин билдер – async {}. Эта функция возвращает объект типа Deferred, в
    // в котором можно получить объект/результат выполнения корутины.
    // Чтобы дождаться результата нужно вызвать метод .await() объекта Deferred
    println("[${Thread.currentThread().name}] Start")
    coroutineScope {
        val deferred: Deferred<Double> = async {
            execSuspend(1000)
        }
        // И чтобы дождаться результата нужно вызвать метод .await() объекта deferred
        val result = deferred.await()
        println("[${Thread.currentThread().name}] Delay time is $result sec.")
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем приложение. В консоле мы видим результат выполнения функции exec()
}

private suspend fun execSuspend(ms: Long): Double {
    println("[${Thread.currentThread().name}] Start do something with delay #$ms ms")
    delay(ms)
    println("[${Thread.currentThread().name}] Finish do something with delay #$ms ms")
    return  ms.toDouble() / 1000
}
