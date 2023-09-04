package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Random

// Если не указать Unit, тогда функция main() будет возвращать последнее занчение из runBlocking{},
// а launch{} возвращает Job.
fun main():Unit = runBlocking {
    launch {
        val result = doWork("Android Broadcast")
        println(result)
    }

}

private suspend fun doWork(name: String): String {
    delay(Random().nextInt(5000).toLong())
    return "Done. $name"
}
