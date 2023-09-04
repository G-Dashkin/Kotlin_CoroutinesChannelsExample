package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.Executor
import java.util.concurrent.Executors


fun main(args: Array<String>) {

    // Запускаем один пример за другим
    exampleThreadBlocking()
    exampleSuspendBlocking()
    exampleBlockingDispatcher()
    exampleLaunchGlobal()
    exampleLaunchGlobalWithing()
    exampleLaunchCoroutineScope()
    exampleLaunchCostumeDispatcher()
    exampleAsyncAwaitOne()
    exampleAsyncAwaitTwo()
    exampleWithContext()

}
fun printDelayedThread(message: String) {
    // Complex calculation
    Thread.sleep(1000)
    println(message)
}

fun exampleThreadBlocking() {
    println("exampleThreadBlocking()-------------------------------------------------")
    println("one")
    printDelayedThread("two")
    println("three")
}

suspend fun printDelayedSuspend(message: String) {
    // Complex calculation
    delay(1000)
    println(message)
}

fun exampleSuspendBlocking() = runBlocking {
    println("exampleSuspendBlocking()-------------------------------------------------")
    println("one")
    printDelayedSuspend("two")
    println("three")
}

// Запуск другого потока но с блокировкой главного потока
fun exampleBlockingDispatcher() {
    println("exampleBlockingDispatcher()-------------------------------------------------")
    runBlocking(Dispatchers.Default) {
        println("one - from thread ${Thread.currentThread().name}")
        printDelayedSuspend("two - from ${Thread.currentThread().name}")
    }
    println("three - from thread ${Thread.currentThread().name}")
}

fun exampleLaunchGlobal() = runBlocking {
    println("exampleLaunchGlobal()-------------------------------------------------")
    println("one - from thread ${Thread.currentThread().name}")
    GlobalScope.launch {
        printDelayedSuspend("two - from ${Thread.currentThread().name}")
    }
    println("three - from thread ${Thread.currentThread().name}")
    delay(3000) // без задержики в лог сообщение не будет выведено
}

fun exampleLaunchGlobalWithing() = runBlocking {
    println("exampleLaunchGlobalWithing()-------------------------------------------------")
    println("one - from thread ${Thread.currentThread().name}")
    val job = GlobalScope.launch {
        printDelayedSuspend("two - from ${Thread.currentThread().name}")
    }
    println("three - from thread ${Thread.currentThread().name}")
    job.join() // Вместо задержки спользуется  .join() и это правильный вариант
}

fun exampleLaunchCoroutineScope() = runBlocking {
    println("exampleLaunchCoroutineScope()-------------------------------------------------")
    println("one - from thread ${Thread.currentThread().name}")
    launch(Dispatchers.Default) {
        printDelayedSuspend("two - from ${Thread.currentThread().name}")
    }
    println("three - from thread ${Thread.currentThread().name}")
}

fun exampleLaunchCostumeDispatcher() = runBlocking {
    println("exampleLaunchCostumeDispatcher()-------------------------------------------------")
    println("one - from thread ${Thread.currentThread().name}")
    val customDispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
    launch(customDispatcher) {
        printDelayedSuspend("two - from ${Thread.currentThread().name}")
    }
    println("three - from thread ${Thread.currentThread().name}")
}

suspend fun calculateHardThings(startNum: Int): Int {
    delay(1000)
    return startNum * 10
}

fun exampleAsyncAwaitOne() = runBlocking {
    println("exampleAsyncAwaitOne()-------------------------------------------------")
    val startTime = System.currentTimeMillis()
    val deferred1 = async { calculateHardThings(10) }
    val deferred2 = async { calculateHardThings(20) }
    val deferred3 = async { calculateHardThings(30) }

    val sum = deferred1.await() + deferred2.await() + deferred3.await()
    println("async/await result = $sum")
    val endTime = System.currentTimeMillis()
    println("Time taken: ${endTime - startTime}")
}

fun exampleAsyncAwaitTwo() = runBlocking {
    println("exampleAsyncAwaitTwo()-------------------------------------------------")
    val startTime = System.currentTimeMillis()
    val deferred1 = async { calculateHardThings(10) }.await()
    val deferred2 = async { calculateHardThings(20) }.await()
    val deferred3 = async { calculateHardThings(30) }.await()

    val sum = deferred1 + deferred2 + deferred3
    println("async/await result = $sum")
    val endTime = System.currentTimeMillis()
    println("Time taken: ${endTime - startTime}")
}

fun exampleWithContext() = runBlocking {
    println("exampleWithContext()-------------------------------------------------")
    val startTime = System.currentTimeMillis()
    val result1 = withContext(Dispatchers.Default) { calculateHardThings(10) }
    val result2 = withContext(Dispatchers.Default) { calculateHardThings(20) }
    val result3 = withContext(Dispatchers.Default) { calculateHardThings(30) }

    val sum = result1 + result2 + result3
    println("async/await result = $sum")
    val endTime = System.currentTimeMillis()
    println("Time taken: ${endTime - startTime}")
}