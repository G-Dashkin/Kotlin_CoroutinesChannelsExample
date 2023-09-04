package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples

// https://www.youtube.com/watch?v=p2qHK31cUE0
fun main() {
    println("Main started")
    println("Thread name: " + Thread.currentThread().name + ", Thread id: " + Thread.currentThread().id)
    for (i in 0..1_000_000) {
        MyThread(Thread.currentThread().name, 100*i.toLong()).start()
    }
    println("Main ended")
}


internal class MyThread(private val threadName: String, val sleepDelay: Long) : Thread(threadName) {
    override fun run() {
        println("Worker Thread Started")
        println("Thread name: " + Thread.currentThread().name + ", Thread id: " + Thread.currentThread().id)
        for (i in 0..9) {
            try {
                sleep(sleepDelay)
                println("Remaining time left for ${Thread.currentThread().name}: " + (10 - i))
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        println("Worker Thread ended")
    }
}