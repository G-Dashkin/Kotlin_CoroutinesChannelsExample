package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    // Корутины намного легче потоков.
    // мы можем создать 100 000 корутин и выполнять в них какие то операции
    // если бы попробовали создать 100 000 потоков, система бы зависла
    var x = 0
    val time = measureTimeMillis {
        runBlocking {
            repeat(100_000) {

                launch {
                    for (i in 1..5) {
                        x +=i
                    }
                }
            }

        }

    }
    println(x)
    println(time)


}