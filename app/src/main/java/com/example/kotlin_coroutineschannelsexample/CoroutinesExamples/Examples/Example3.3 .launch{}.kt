package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    println("Start no Blocking")
    runBlocking {
        for (i in 1..5) {
            // Теперь попробуем “распараллелить” вызовы функции execSuspend(i)
            // Для этого обернем все вызовы функции execSuspend() в функцию launch{}
            // Таким образом мы по сути запускаем функцию каждый раз в отдельной корутине
            // По сути функция launch{} просто запускает корутину и возвращает управление вызывающему
            // потоку. И так как теперь вызывающий поток у нас не блокируется на время выполнения
            // execSuspend(), мы можем вызвать эту функцию несколько раз подряд.
            // И дождаться завершения каждого вызова.
            launch {
                execSuspend(i)
            }
        }
    }
    println("Finish no Blocking")
    // Запускаем программу и теперь все вызовы exec() произошло практически одновременно.
}

private suspend fun execSuspend(i: Int) {
    println("Start do something #$i")
    delay(1000)
    println("Finish do something #$i")
}