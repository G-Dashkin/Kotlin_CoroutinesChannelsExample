package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

suspend fun main() {
    // Перепишим наш код на Flow – аналог sequence из асинхронного мира.
    val time = measureTimeMillis {

        withContext(newSingleThreadContext("Single_thread_context")){
            launch {
                repeat(3) {
                    delay(100)
                    println("[${Thread.currentThread().name}] I'm working!")
                }
            }
            launch {
                // .forEach{} заменили на .collect{}
                getList().collect() { i ->
                    println("[${Thread.currentThread().name}] Receive value $i")
                }
            }
            // Обращаем внимание, что генерация элемнтов не будет вызвана до тех пор пока не будет
            // вызвана терминальная функция, в данном случае это .collect{}
        }
    }
    println("[${Thread.currentThread().name}] Completed in $time ms")
    
    // Запускаем код
    // Теперь мы знаем как создать асинхронный поток при помощи конструктора и специальных билдеров
    // и получить все его элементы при помощи метода .collect{}

}

// Заменем sequence на flow
private suspend fun getList() = flow {
    for (i in 1..3) {
        delay(100)
        println("[${Thread.currentThread().name}] Generate value $i")
        // заменили yield() на emit()
        emit(i)
    }
}

// Для создания flow {} мы можем использовать специальные билдеры .asFlow() или flowOf()
private suspend fun getList2() = (1..3).asFlow()
private suspend fun getList3() = flowOf(1..3)