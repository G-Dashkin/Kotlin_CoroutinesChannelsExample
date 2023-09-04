package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    // Теперь убедимся, что родительская корутина при принудительном заввершении работы отменяет все
    // дочерние корутины.  Изменем наш код,
    println("[${Thread.currentThread().name}] Start")
    val time = measureTimeMillis {
        runBlocking {
            println("[${Thread.currentThread().name}] Outer scope")
            val job1 = launch {
                println("[${Thread.currentThread().name}] Inner scope 1")
                launch {
                    println("[${Thread.currentThread().name}] Inner launch 1")
                    delay(2000)
                    println("[${Thread.currentThread().name}] It will never be printed")
                }
                launch {
                    println("[${Thread.currentThread().name}] Inner launch 2")
                    delay(3000)
                    println("[${Thread.currentThread().name}] It will never be printed")
                }
                GlobalScope.launch {
                    println("[${Thread.currentThread().name}] Global Scope launch")
                    delay(4000)
                }
            }
            launch {
                println("[${Thread.currentThread().name}] Inner Scope 2")
                delay(1000)
                job1.cancelAndJoin()
            }
        }
    }
    println("[${Thread.currentThread().name}] Execute time: $time")
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем код и через секунду выполнение всех корутин было отменено.

}