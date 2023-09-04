package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    var channel: ReceiveChannel<Language> = Channel()

    runBlocking {
        launch {
            // Убираем параметр capacity
            channel = produce {
                send(Language.Kotlin)
                println("Kotlin Sent!")
                send(Language.Java)
                println("Java Sent!")
                send(Language.Python)
                println("Python Sent!")
                send(Language.JavaScript)
                println("JavaScript Sent!")
            }
        }

        launch {
            // Получаем элементы через .receive() с задержкой в 3 секунды
            // В итоге помещаем в канал только первый и второй элементы и проучаем их
            println(channel.receive())
            delay(3000)
            println(channel.receive())
        }
    }


}
