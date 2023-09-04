package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    // Рассмотрим смену контекста при запуске корутины.
    println("[${Thread.currentThread().name}] Start")
    runBlocking {
        newSingleThreadContext("Context_1").use { ctx1 ->
            newSingleThreadContext("Context_2").use { ctx2 ->
                // И сделем смену контекста корутины.
                withContext(ctx2) {
                    println("[${Thread.currentThread().name}] Start in context 1")
                    withContext(ctx2) {
                        println("[${Thread.currentThread().name}] Work in context 2")
                    }
                    println("[${Thread.currentThread().name}] End in context 2")
                }
            }
        }
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем код и здесь мы начали в контексте №1, затем переключились во второй контекст.
    // И завершили выполнение в первом контексте
    // Мы по сути отменяли выполнение корутины, если она не успела выполниться за отведенное время.
}