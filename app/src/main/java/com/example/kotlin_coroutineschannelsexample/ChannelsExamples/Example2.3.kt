package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
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
            channel.close() // пробудем закрыть канал после помещения последнего закрытия
            // И после запуска видим, что канал закрыт при вызове метода .isClosedForReceive
        }

        launch {
            println(channel.isClosedForReceive)
            println(channel.receive())
            println(channel.receive())
            println(channel.isClosedForReceive)
        }

    }


}
