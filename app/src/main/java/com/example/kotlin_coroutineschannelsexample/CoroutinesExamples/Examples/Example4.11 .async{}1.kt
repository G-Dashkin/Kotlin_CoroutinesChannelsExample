package com.example.kotlin_coroutineschannelsexample.CoroutinesExamples.Examples

import kotlinx.coroutines.*

fun main() = runBlocking {

    // Другой способ запустить корутину, например нам нужно отправить запрос к серверу и получить
    // какой то результат. Для этого у нас есть другой корутинБилдер – async{} он возвращает
    // значение – Deferred какого то типа.

    println("Main " + Thread.currentThread().name)
    // запустим корутину в которой мы будем считать сумму чисел от 0 до 1000 и чтобы все происходило
    // не очень быстро, будем усыплять поток на 100 мллесекунд.
    // Поместим возвращаемое значение в отдельную корутину
    val deferred = async {
        var sum = 0
        for (i in 0..1000) {
            sum+= i
            delay(100)
        }
        // И теперь укажем, что этот async{} должен вернуть эту сумму. В котлине слово returne можно опускать.
        sum
    }

    // Для того, чтобы дождаться значения в этой переменной, есть специальный метод - .awit{}
    // Выведем в лог, содержимое переменной result и с применением .awit{}
    // При этом при вызове .awit{} указан тип переменной, который мы передали. Компилятор сам определять тип.
    println(deferred)
    println(deferred.await())

    // И таким образом шаблон Deferred<T> преобразуется в Deferred<Int> для конкретного значения.

    // Запускаем код. У нас выводится ссылка на эту корутину, но пока ничего не выводится и работа
    // приложения не завершена из за delay() слишком долго придется ждать.

}



