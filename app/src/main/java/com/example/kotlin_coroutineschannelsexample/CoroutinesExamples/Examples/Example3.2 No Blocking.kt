package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

    // Для понимания работы корутин, рассмотим код в котором последоваттельно вполняется несколько
    // блокирующих вызовов. У нас есть функция execBlocking() принимающая число и выводящая в лог.
    // В этой функции вызывающий поток блокируется на 1 секунду и в цикле сделаем выполнение 5
    // последовательных вызовов этой функции, передавая туда чило т.е номер вызова цикла.
    // Запускаем код и посмотрим как он работает. Ничего необычного, просто последовательные вызовы.
    println("Start Blocking")
    for (i in 1..5) {
        // Каждый вызов функции exec() блокирует вызывающий поток на время своего выполнения.
        execBlocking(i)
    }
    println("Finish Blocking")

    println("--------------------------------------------------------------------------------------")
    // И запустим такой же пример но в не блакирующей функции

    // Чтобы этот код работал запустим его в runBlocking
    // runBlocking{} является “мостиком” между блокирующим и не блокирующим кодом. Она создает
    // область корутин и дожидается выполнения всех связанных с ней операций.
    // При этом поток функции main() блокируется на время вызова runBlocking{}, а внутри она работает ассинхронно.
    println("Start no Blocking")
    runBlocking {
        for (i in 1..5) {
                execSuspend(i)
            }
    }
    println("Finish no Blocking")

    // В итоге обе функции работают одинаково, но вторая не блокирует поток.
    // т.е. теперь функция execSuspend() при вызове delay() сохраняет свое состояние в памяти и
    // освобождает вызывающий поток. А после получения результата восстанавливает свое состояние и
    // продлжает свое выполнение.
}

// Блокирующая функция усыпляющая поток через Thread.sleep
private fun execBlocking(i: Int) {
    println("Start do something #$i")
    Thread.sleep(1000)
    println("Finish do something #$i")
}

// Не блокирующая suspend-функция
private suspend fun execSuspend(i: Int) {
    println("Start do something #$i")
    delay(1000)
    println("Finish do something #$i")
}