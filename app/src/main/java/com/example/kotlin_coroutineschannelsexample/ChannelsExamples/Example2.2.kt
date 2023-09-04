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
        }

        launch {
            // Вызовим метод .receive() два раза и метод .isClosedForReceive из канала
            // Видим, что канал не закрыт, возвращает false
            println(channel.isClosedForReceive)
            println(channel.receive())
            println(channel.receive())
            println(channel.isClosedForReceive)
        }
        // Важно!!! Обращаем, внимание, что при запуске программы, она все еще работает, так как
        // канал не зекрыт

    }


}
