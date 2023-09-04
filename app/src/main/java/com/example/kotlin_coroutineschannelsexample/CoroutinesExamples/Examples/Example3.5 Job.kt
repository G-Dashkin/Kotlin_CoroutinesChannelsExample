package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

suspend fun main() {
    println("Start")
    // Создадим еще одну область корутины – coroutineScope {} внтури которой запустим одну корутину
    // и в ней вызовем нашу execSuspend(). Корутин билдер launch {} возвращает объект типа Job.


    coroutineScope {
        // Имея объект типа Job мы может отложить запуск корутины. Чтобы это сделать это, нужно
        // установить признак отложенного запуска в функции launch {} - CoroutineStart.LAZY
        val jobLAZY: Job = launch(start = CoroutineStart.LAZY) {
            execSuspend(500)
        }
        launch {
            delay(1000)
            println("Before stat() LAZY")
            jobLAZY.start()
        }

        delay(3000) // сделаем искуственную задержку, чтобы сработала одна корутина за другой
                            // p.s. это не правильный способ обозначения последовательности работы
                            // корутин. Нужно использовать .join()
        println("---------------------------------------------------------------------------------")
        // Если мы не укажем праметр отложенного запуска LAZY,то порядок выполнения будет не правильный.
        // Сначала запустится и выполится корутина и только затем запустится и выполнится строка Before stat()
        // т.е. метод .start() у джобы не сработает, даже если мы его вызовим
        // А если мы добавим праметр отложенного запуска LAZY и не вызовоим метод .start(), то
        // программа просто зависнет т.е. она будет безкончено ожидать запуска корутины.
        val jobDefault: Job = launch() {
            execSuspend(500)
        }
        launch {
            delay(1000)
            println("Before stat() Default")
//            jobDefault.start()
        }

    }
    println("Finish")

    // Запускаем код и в консоле мы видим правильный порядок запуска. В первом примере саначала вывелось
    // Before stat(), затем выполнилась сама корутина и строка finish




}

private suspend fun execSuspend(ms: Long) {
    println("Start do something with delay #$ms ms")
    delay(ms)
    println("Finish do something with delay #$ms ms")
}
