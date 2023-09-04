package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

fun main() {
    // В данном примере все функции выполняются последовательно
    // запускаем функцию прерывания потока без создания отдельного потока
    println("main starts")
    routineFun(1, 500)
    routineFun(2, 300)
    println("main ends")
}

fun routineFun(number: Int, delay: Long){
    println("Routine $number starts work")
    Thread.sleep(delay)
    println("Routine $number has finished")
}