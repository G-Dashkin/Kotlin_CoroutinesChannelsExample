package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Random
// Теперь запустим множество корутин и обратим внимание на порядок
fun main():Unit = runBlocking {
    repeat(100) {
        launch {
            val result = doWork(it.toString())
            println(result)
        }
    }
    // Порядка нет, так как мы запускаем suspend-функции в отдельных корутинах
}

private suspend fun doWork(name: String): String {
    delay(Random().nextInt(5000).toLong())
    return "Done. $name"
}
