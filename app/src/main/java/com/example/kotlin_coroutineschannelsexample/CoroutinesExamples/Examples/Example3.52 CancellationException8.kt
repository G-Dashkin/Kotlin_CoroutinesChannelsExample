package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {

    // 1) При этом, если исключение возникнет сразу в нескольких дочерних корутинах, то обработано будет
    // то, которое возникло первым. Остальные будут добавлены к первому как suppressed
    println("[${Thread.currentThread().name}] Start execution")
    val handler = CoroutineExceptionHandler { _, exeption ->
                                                                    // 2) Отметем это в коде.
        println("[${Thread.currentThread().name}] Handle execution: $exeption with suppressed: ${exeption.suppressed.contentToString()}")
    }
    val job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE)
                // 3) Уберем блок catch() и изменем finally{}
//            } catch (e: CancellationException) {
//                println("[${Thread.currentThread().name}] Children are cancelled")
            } finally {
                // 4) В блоке уберем withContext(NonCancellable) и добавим throw IllegalArgumentException()
//                withContext(NonCancellable) {
                println("[${Thread.currentThread().name}] First child throws an exception")
                throw IllegalArgumentException()
//                }
            }
        }
        launch {
            delay(10)
            println("[${Thread.currentThread().name}] Second child throws an exception")
            throw ArithmeticException()
        }
    }
    job.join()
    println("[${Thread.currentThread().name}] End execution")

    // Запускаем код, у нас выводится первое исключение и все дополнительные.
}