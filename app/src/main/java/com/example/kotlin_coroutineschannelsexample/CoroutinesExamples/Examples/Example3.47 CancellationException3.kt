package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {
    // Добавим специльный обработчик исключений к контексту корутин из прошлого примера.
    val handler = CoroutineExceptionHandler { context, exception ->
        println("[${Thread.currentThread().name}] Handler exception $exception")
    }
    val job = GlobalScope.launch(handler) {
        println("[${Thread.currentThread().name}] Handler exception from launch")
        throw  ArithmeticException()
    }
    try {
        job.join()
        println("[${Thread.currentThread().name}] joined failed job")
    } catch (e: ArithmeticException) {
        println("[${Thread.currentThread().name}] Caught ArithmeticException")
    }

    // Запускаем приложение, Искоючение обрабатывается, но обращаем внимание, что мы не можем
    // восстановить выполнение в этом обоработчике, потму что к моменту, когда он быдет вызван, корутина
    // уже завершит свое выполнение и основное его назначение это логирование и исключение.

    // Обработчик CoroutineExceptionHandler имеет смысл определять только на корневой корутине, так
    // как дочерние корутины всегда делегируют обработку своих исключений корневой корутине

    // При этом построитель async{} соберает вообще все исключения возникшие в дочерних корутинах в
    // результирующем Deferred объекте. Поэтому для него определенеи хэндлера не будет иметь вообще
    // никакого эффекта.


}