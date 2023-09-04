package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(array: Array<String>) = runBlocking {

    // Перейдем к контексту – он имеет набор элементов для определения поведения корутины.
    // CoroutineDispatcher - определяет поток, в котором будет выполняться код
    // CoroutineName - имя корутины
    // Job – это специальный объект, который содержит ссылку на корутину и может ее останавливать.

    println(coroutineContext[Job])
    val job = launch {
        println(coroutineContext[Job])
    }

    // Запускаемм программу, смотрим лог
    // В данном примере у нас для корутины, которая runBlocking{} выведена ссылка на ее Job (т.е.
    // ссылка на саму эту корутину) и в логи выведется BlockingCoroutine и в нем Active – это
    // состояние корутины, т.е. в данный момент она была активна, она работала когда это выводилось

    // Здесь видно, что это блокирующая корутина (BlockingCoroutine) т.к. мы создали ее с помощью
    // runBlocking{} и она будет дожидаться выполнения всего того, что у нее внутри т.к. этого требует
    // фукнцкия main()

    // Вторая, внутренняя корутина в лог выводит StandaloneCoroutinen т.е. это обычная классическая
    // корутина, ничего не блокирующая и она в тот момент тоже была активна.

    // Scope – это область жизни корутины
    // Context – набор конкретных параметров корутины.

}



