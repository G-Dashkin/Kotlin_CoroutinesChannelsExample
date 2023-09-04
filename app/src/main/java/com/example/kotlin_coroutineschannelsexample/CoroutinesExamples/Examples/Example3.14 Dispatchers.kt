package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() {
    // Каждый КорутинБилдер принимает в качестве необязательного параметра Context, который можно
    // использовать для явного указания контекста(и соответственно диспетчера). Если диспетчер не
    // указан, то будет использоваться диспетчер родительского скоупа.

    // Рассмотрим диспетчеры на примерах и сделаем отдельную переемнную для вывода поток в лог.
    println("[${Thread.currentThread().name}] Start")
    runBlocking {
        launch {
            println("[${Thread.currentThread().name}] Main runBlocking")
        }
        launch(Dispatchers.Default) {
            println("[${Thread.currentThread().name}] Dispatchers.Default")
        }
        launch(Dispatchers.IO) {
            println("[${Thread.currentThread().name}] Dispatchers.IO")
        }
        launch(newSingleThreadContext("My single thread context")) {
            println("[${Thread.currentThread().name}] My single thread context")
        }
        launch(Dispatchers.Unconfined) {
            println("[${Thread.currentThread().name}] Dispatchers.Unconfined")
        }
    }
    println("[${Thread.currentThread().name}] Finish")

    // Запускаем приложение.

    // 1.При вызове launch {} без параметров используется диспатчер из контекста коутины – runBlocking{},
    // которая запущена в потоке main

    // 2.Dispatchers.Default запускает корутину в дефолтном диспетчере.

    // 3.Dispatchers.IO - специальный диспетчере, для запуска оперций ввода/вывода. Потоки для запуска
    // корутин с этим диспетчером создаются и закрываются по требованию

    // Диспетчеры Default и IO разделяют между собой общий пулл потоков. Это видно по названию
    // используемых воркеров. Это сделано для того, чтобы не переключать контекст выполнения при смене
    // диспетчера Default на IO внутри корутины. Однако количество потоков доступных Dispatchers.Default
    // ограничено колличеством ядер процессора.

    // 4.Dispatchers.Unconfined запускает корутину в вызывающем потоке, но только до момента первой
    // приостановки. После возобновления корутина продолжает выполнение в том потоке, который был
    // определен вызванной suspend-функцией
    // Для демонстрации этого добавим току приостановки корутины. Корутина начала свое выполнение в
    // потоке main, а после пиостановки продолжила выполнение в DefaultExecutor
    // Заменить, на это
//    launch(Dispatchers.Unconfined) {
//        println("[${Thread.currentThread().name}] Start with Dispatchers.Unconfined")
//        delay(500)
//        println("[${Thread.currentThread().name}] Resume with Dispatchers.Unconfined")
//    }
//    Такой диспетчер удобно использовать, когда у нас есть редко выполняемыая ветка кода, содержащая
//    suspend-функцию. И при использовании этого диспетчера нам в большинстве случаев не придется менять
//    контекст выполнения.

    // 5. newSingleThreadContext – создает отдельный поток для запуска корутины. Создание потока
    // довольно ресурозатратная операция, поэтому в реалх приложениях такой контекст стоит использовать
    // с остарожностью.

}