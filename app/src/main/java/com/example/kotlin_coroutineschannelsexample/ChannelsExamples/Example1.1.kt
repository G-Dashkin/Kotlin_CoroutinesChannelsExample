package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    val kotlinChannel = Channel<String> ()  // 1) Создаем стринговый канал
    val charList = arrayOf("A","B","C","D") // 2) Создаем массив строк

    runBlocking {

        // 3) В корутине через метод .send() помещаем элементы массива в стринговый канал
        launch {
            for (char in charList) {
                kotlinChannel.send(char)
            }
        }
        // 4) В другой корутине из стринового канала получаем элементы, которые мы туда передали
        launch {
            for (char in kotlinChannel) {
                println(char)
            }
        }
    }

    // Таким образом мы реалиовали способ передачи данных между корутинами в каналах
}