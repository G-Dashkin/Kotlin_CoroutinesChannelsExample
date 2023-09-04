package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    // Создаем канал другим способом, через ReceiveChannel
    var channel: ReceiveChannel<Language> = Channel()

    runBlocking {
        launch {
            // При помещении значений в канал нем не нужно закрывать канал вручную
            // produce - служит для генерации кончных или бесконечных значений
            channel = produce {
             send(Language.Kotlin)
             send(Language.Java)
            }
        }

        launch {
            // Запускаем перебор канала и проверку открыт ли канал.
            // канал закрывается сам, когда отработал
            println(channel.isClosedForReceive)
            channel.consumeEach {
                println(it)
            }
            println(channel.isClosedForReceive)
        }

    }


}
