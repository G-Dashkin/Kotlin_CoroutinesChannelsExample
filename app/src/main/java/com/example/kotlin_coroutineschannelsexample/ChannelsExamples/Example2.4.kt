package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    val channel = Channel<Language> ()

    runBlocking {
        launch {
            println("Kotlin Sent!")
            channel.send(Language.Kotlin)
            println("Java Sent!")
            channel.send(Language.Java)
            channel.close()
        }

        launch {
            // вместо метода receive() можно использовать .consumeEach{} чтобы получить все значения
            channel.consumeEach {
                println(it)
            }
        }

    }


}
