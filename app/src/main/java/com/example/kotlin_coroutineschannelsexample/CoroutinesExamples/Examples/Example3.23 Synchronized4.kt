package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {

    val c = 100
    val r = 1000
    // Вторым способом решения проблемы с парралельным доступом, это использование потокобезопасных
    // типов данных, которые позволяют выполнять над ними атомарные оперции
    var counter = AtomicInteger(0)
    val singleThreadContext = newSingleThreadContext("Update_context")

    val time = measureTimeMillis {
        withContext(singleThreadContext) {
            repeat(c) {
                launch {
                    repeat(r) {
                        // И у него есть функция .incrementAndGet() которая позволяет установить значение и получить его
                        counter.incrementAndGet()
                    }
                }
            }

        }
    }
    println("[${Thread.currentThread().name}] Completed ${c * r} actions in $time ms")
    println("[${Thread.currentThread().name}] Completed = $counter")

    // Запускаем код, время выполнения осталось тем же и состояия гонки нет.

}