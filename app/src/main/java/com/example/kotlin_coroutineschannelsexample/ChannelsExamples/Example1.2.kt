package com.example.kotlin_coroutineschannelsexample.ChannelsExamples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    val kotlinChannel = Channel<String> ()
    val charList = arrayOf("A","B","C","D")

    runBlocking {

        launch {
            for (char in charList) {
                kotlinChannel.trySend(char).isSuccess
            }
        }

        for (char in kotlinChannel) {
            println(char)
        }

    }

}