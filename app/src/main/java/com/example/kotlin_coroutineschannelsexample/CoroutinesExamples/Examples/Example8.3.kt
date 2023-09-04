package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Random
// Если мы запустим нашу функцию без корутины, но все будет запускаться в последовательном порядке
// т.е. сами suspend функции последовательны и пока не закончится выполнение одной suspend-функции
// следующая не будет запущена
fun main():Unit = runBlocking {
    repeat(100) {
        val result = doWork(it.toString())
        println(result)
    }
}

private suspend fun doWork(name: String): String {
    delay(Random().nextInt(5000).toLong())
    return "Done. $name"
}
