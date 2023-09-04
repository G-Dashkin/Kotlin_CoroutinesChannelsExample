package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {
    // Разберем тему обработки исключений
    // При отмене корутина выбрасывает исключение – CancellationException и это исключение по умолчанию
    // игнорируется механизмом корутин.
    // CancellationException - игнорируется механизмом корутин
    // async и produce возлагают обработку исключений на вызывающую строку
    // launch и actor обращаются с исключениями как с необраатываемым
    val deffered = GlobalScope.async {
        println("[${Thread.currentThread().name}] Throwing exception from async")
        throw ArithmeticException()
    }
    try {
        deffered.await()
        println("[${Thread.currentThread().name}] Unreached")
    } catch (e: ArithmeticException) {
        println("[${Thread.currentThread().name}] Caught ArithmeticException")
    }

    // Запускаем приложение и Посмотрим, что произойдет, если корутина выбросит исключение в процессе
    // отмены или несколько дочерних корутин выбросят исключения.
    // Исключение было выброшено наружу в момент выозов await() и успешно перехвачено.

}