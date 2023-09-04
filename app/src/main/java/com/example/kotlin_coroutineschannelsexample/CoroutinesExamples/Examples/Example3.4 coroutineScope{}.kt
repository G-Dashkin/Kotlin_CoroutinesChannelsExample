package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// Создать область корутин можно и без запуска корутины с помощью специальной функции – сoroutineScope{}.
// Рассмотрим на примере,
// Напишем функцию exec(), которая будет имитировать выполнение определенной работы в течении
// заданного времени. Создадим область корутины и внутри нее запустим 3 корутины подряд, в каждой из
// которой будет вызваться наша функция. У каждой функции время работы будет разное. Функцию main()
// при этом сделаем suspend.

suspend fun main() {
    println("Start")
    coroutineScope {
        launch {
            execSuspend(1000)
        }
        launch {
            execSuspend(500)
        }
        launch {
            execSuspend(800)
        }
    }
    println("Finish")

    // Запускаем код,
    // В консоль сначала была выведена строка Start, затем была создана область корутины, внутри которой
    // подряд были запущены 3 корутины. И эти три корутины завершили свое выполнение через заданный
    // промежуток времени. Затем область корутины была закрыта и в консоль была выведена строка Finish

    // Таким образов область корутины завершает свое выполнение и возвращает выполнение вызывающему
    // коду только тогда, когда будут завершены все выполняющиеся в ней корутины.
}

private suspend fun execSuspend(ms: Long) {
    println("Start do something with delay #$ms ms")
    delay(ms)
    println("Finish do something with delay #$ms ms")
}
