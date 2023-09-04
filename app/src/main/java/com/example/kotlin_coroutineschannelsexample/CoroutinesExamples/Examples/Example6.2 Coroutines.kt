package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// Реализуем такой же пример с использованием корутин
fun main() = runBlocking {
    println("main starts")
    // Вызываем метод joinAll() чтобы дождаться выполнения обеих корутин
    joinAll(
        async { coroutineFun(1, 500) },
        async { coroutineFun(2, 300) }
    )
    println("main ends")
    // Запускаем приложение обе корутины последоватьно запускаются и завершают работу
    // обращаем вримание на время которое мы установили в корутинах
}

// Создадим suspend-функцию в которой будет периостанавливаться выполнение
suspend fun coroutineFun(number: Int, delay: Long){
    println("Routine $number starts work")
    delay(delay) // Вместо Thread.sleep() вызываем delay()
    println("Routine $number has finished")
}