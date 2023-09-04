package com.example.kotlin_coroutineschannelsexample.LooperHandlerMessageQueue

import android.os.Handler
import android.os.Looper
import android.util.Log

class LooperThread: Thread() {

    // Создадим хэндлер в этом потоке, что мы могли помещать в него объекты Runnable
    lateinit var handler: Handler
    // Создадим лупер для управления потоком
    lateinit var looper: Looper

    override fun run() {
        super.run()
        // Но просто так нельзя создать хэндлер
        // Для его создания нужно заначана вызвать метод prepare() у хэндлера
        Looper.prepare()
        looper = Looper.myLooper()!!

        // Для отправки сообщения через .sendMessage() нужно создать не просто класс Handler()
        // а хэндлер в котором переоределн метод handleMessage()
//        handler = Handler()
        handler = HandlerMessage()

        // И вызовим метод .loop(), чтобы создать очередь. Без этого мы не сможем ничено отправляь
        // в хэндлер. Без этого методы сразу будет выведено сообщение в лог
        Looper.loop()
        Log.d("MyLog", "End of run()")
    }

}