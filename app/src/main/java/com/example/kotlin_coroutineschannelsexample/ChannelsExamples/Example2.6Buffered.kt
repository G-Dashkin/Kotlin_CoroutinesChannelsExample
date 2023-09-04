package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
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
            // Указываем размер капасити канала на 2 элемента, но при этом помещаем 4 эелемента в канал
            // это и есть Buffered
            channel = produce(capacity = 2) {
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
            // И в другой корутине получаем элементы из канала 4 раза
            println(channel.receive())
            delay(3000)
            println("----")
            println(channel.receive())
            delay(3000)
            println("----")
            println(channel.receive())
            delay(3000)
            println("----")
            println(channel.receive())
            // Запускаем прилоежине. В итоге первые 2 элемента мы поменстили в канал сразу, далее
            // получили два элемента и по очереди поместили еще два элемента и получили каждый из элементов
        }
    }


}
