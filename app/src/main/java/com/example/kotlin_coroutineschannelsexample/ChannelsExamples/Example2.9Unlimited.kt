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
            // Указываем параметр UNLIMITED
            channel = produce(capacity = UNLIMITED) {
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
            // Помещаем в канал все элементы, но получаем только первый и втрой элементы (в отличае от Rendezvous)
            println(channel.receive())
            delay(3000)
            println(channel.receive())
        }
    }


}
