package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    // Сейчас вывод в консоль происходит без обозначения области и потока. Потому, что в лог выводится
    // через Run, а не через лог. Попробуем добавить их вручную. И теперь мы видим названия потоков.
    println("[${Thread.currentThread().name}] Start")
    coroutineScope {
        // Так же с помощью объекта Job можно дождаться выполнения корутины внутри области.
        val job: Job = launch() {
            execSuspend(1000)
        }
        println("[${Thread.currentThread().name}] Before join()")
        job.join() // Если не вызвать .join(), то программа не будет дожиться окончания работы корутины и сразу выведит в лог Before join() и After join()
        println("[${Thread.currentThread().name}] After join()")
    }
    println("[${Thread.currentThread().name}] Finish")
    // Запускаем программу. Сначала в консоль выведем Before join(), затем запустим корутину и
    // выведемм After join(),
}

private suspend fun execSuspend(ms: Long) {
    println("[${Thread.currentThread().name}] Start do something with delay #$ms ms")
    delay(ms)
    println("[${Thread.currentThread().name}] Finish do something with delay #$ms ms")
}
