package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*
import java.util.Random
// В отличает от билдера launch {}, билдер async{} позволяет возвращать результат
// В данном примере мы возвращаем результат из корутины, а не в самой корутине.
// Сохраним наши 100 корутин в списк и при помощи функции .await() мы дожидаемся окончания их работы
fun main(): Unit = runBlocking {
    val coroutines: List<Deferred<String>> = List(100) {
        async {
            doWork(it.toString())
        }
    }
    coroutines.forEach { println(it.await()) }
    // Запускаем и все выполняется последовательно но намного быстрее, чем отдельно запускать
    // suspend-функцию
}

private suspend fun doWork(name: String): String {
    delay(Random().nextInt(5000).toLong())
    return "Done. $name"
}
