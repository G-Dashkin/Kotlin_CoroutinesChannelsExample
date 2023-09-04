package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.concurrent.thread

// Теперь запустим несоклько корутин с разным временем
// Смотрим лог
fun main() = runBlocking {
    println("main starts")
    joinAll(
        async{ suspendingCoroutine(1, 500) },
        async{ suspendingCoroutine(3, 300) },
        async{
            repeat(5) {
                println("other task is working on ${Thread.currentThread().name}")
                delay(100)
            }
         }
    )
    println("main ends")
}

suspend fun suspendingCoroutine(number: Int, delay: Long){
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished on ${Thread.currentThread().name}")
}