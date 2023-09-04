package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

// Рассмотрим пример Structured Concurrency. Когда дочерние корутины зависят от курутины родителя
fun main() = runBlocking {

    // 2) У нас есть корутина выполняющая свою работу в общем GlobalScope-е. Внутри запущены еще две
    // корутины с suspend-функциями
    val job: Job = GlobalScope.launch() {
        // 3) Внутри основной корутины запустим еще две с получением их джобов
        val child1 = launch { println(getUserFromNetwork()) }
        val child2 = launch { println(getUserFromDatabase()) }
        joinAll(child1, child2) // 4) Добавим метод ожидания окончания обеих корутин
    }

    // 5) Отменем работу общей корутины и тогда две другие корутины так же отменятся
    delay(1500)
    job.cancel()

    // Запускаем и в лог выводится только "Tom", так как через 1,5 секунды родительская корутина отменяется

}

// 1) Создадим 2 suspend-функции имитирующие полученние данных из сети и из базы данных
private suspend fun getUserFromNetwork(): String {
    delay(1000)
    return "Tom"
}

private suspend fun getUserFromDatabase(): String {
    delay(2000)
    return "Mike"
}

