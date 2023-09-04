package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() {

    val channel = Channel<Language> ()  // 1) Создаем канал параметризированный классом Language

    runBlocking {
        // 2) В корутине через метод .send() помещаем элементы класса Language в канал
        launch {
            println("Kotlin Sent!")
            channel.send(Language.Kotlin)
            println("Java Sent!")
            channel.send(Language.Java)
        }

        // 4) В другой корутине из стринового канала получаем элементы, которые мы туда передали
        // через метод .receive()
        launch {
            // В итоге при запуске корутины мы получим только один элемент, так как второй еще небыл помещен.
            println(channel.receive())
//            println(channel.receive()) // Если вызвать метод .receive() еще раз, то получим втрое занчение
        }
    }


}

enum class Language{
    Kotlin,
    Java,
    Python,
    JavaScript
}