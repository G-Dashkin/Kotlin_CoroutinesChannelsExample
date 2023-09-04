package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.system.measureTimeMillis

suspend fun main() {
    // Асинхронные потоки имеют терминальные функции, они позволяют получить элементы из потока.
    // Примеры ниже,
    // .toList() - преобразование в список
    // .toSet() - перобразовать в сет
    // .first() - первый элемент
    // .last() - последний элемент
    // .single() - единственный элемент
    // .reduce{} - агрегация
    // .fold(){} - агрегация

    println("[${Thread.currentThread().name}] toList(): ${(1..3).asFlow().toList()}")
    println("[${Thread.currentThread().name}] toSet(): ${(1..3).asFlow().toSet()}")
    println("[${Thread.currentThread().name}] first(): ${(1..3).asFlow().first()}")
    println("[${Thread.currentThread().name}] last(): ${(1..3).asFlow().last()}")
    println("[${Thread.currentThread().name}] single(): ${flowOf(1).single()}")
    println("[${Thread.currentThread().name}] reduce(): ${(1..3).asFlow().reduce { acc, i -> acc * i }}")
    println("[${Thread.currentThread().name}] reduce(): ${(1..3).asFlow().fold(5) { acc, i -> acc * i }}")
}