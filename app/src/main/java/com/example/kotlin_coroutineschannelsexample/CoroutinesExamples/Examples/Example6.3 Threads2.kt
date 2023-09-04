package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

// Теперь запуситим функцию прерывания в отдельном потоке
fun main() {
    println("main starts")
    threadFun(1, 500)
    threadFun(2, 300)
    Thread.sleep(1000)
    println("main ends")
    // Запускаем приложение и обе функции работают так же как и корутины
}

// Создадим функцию прерывания внутри которой запускается поток
// т.е. в этой функции будет прерываться не основной поток, а поток создаваемый в функции
fun threadFun(number: Int, delay: Long){
    thread { // в функции будет каждый раз создаваться отдельный поток
        println("Routine $number starts work")
        Thread.sleep(delay) // Возвращаем Thread.sleep() но вызываем его в отдельном потоке
        println("Routine $number has finished")
    }
}