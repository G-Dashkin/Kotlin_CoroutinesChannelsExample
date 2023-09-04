package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    var channel: ReceiveChannel<Language> = Channel()

    runBlocking {
        launch {
            // в этом же примере перебираем в качесте кампасити указываем CONFLATED
            channel = produce(capacity = CONFLATED) {
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
            // полученные элементы через .consumeEach{} и получаем только первый и последний элементы
            channel.consumeEach {
                println(it)
            }
        }
    }


}
