package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {
    //    Но если в дочерней корутине возникнет любое другое исключение, кроме CancellationException,
    //    то родительская корутина сначал завершит выполнение всех дочерних корутин, а затем завершит
    //    свое собственное выполнение обработав исключение.

    //    Изменем код. Первая корутина нигде явно не отменяется, но она будет отменена неявно, после
    //    возникновения исключения во второй корутине.
    println("[${Thread.currentThread().name}] Start execution")
    val handler = CoroutineExceptionHandler { _, exeption ->
        println("[${Thread.currentThread().name}] Handle execution: $exeption")
    }
    val job = GlobalScope.launch(handler) {
        launch {
            try {
                delay(Long.MAX_VALUE)
            } catch (e: CancellationException) {
                println("[${Thread.currentThread().name}] Children are cancelled")
            } finally {
                withContext(NonCancellable) {
                    delay(100)
                    println("[${Thread.currentThread().name}] The first child finished")
                }
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

    // Запускаем код. Сначала возникло исключение во второй корутине
    // Затем родительская корутина отменила выполнение всех дочерних корутин и обработало исключение.
    // При этом, если исключение возникнет сразу в нескольких дочерних корутинах, то обработано будет
    // то, которое возникло первым. Остальные будут добавлены к первому как suppressed
}